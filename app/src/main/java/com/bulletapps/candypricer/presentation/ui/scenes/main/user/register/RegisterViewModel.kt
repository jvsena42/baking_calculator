package com.bulletapps.candypricer.presentation.ui.scenes.main.user.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.response.LoginResponse
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitEmailUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitPasswordUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyTextUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidatePasswordConfirmationUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.domain.usecase.user.CreateUserUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.PreferencesKeys.ACCESS_TOKEN
import com.bulletapps.candypricer.presentation.util.visualTransformation.MaskPatterns.BR_PHONE_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val submitEmailUseCase: SubmitEmailUseCase,
    private val submitPasswordUseCase: SubmitPasswordUseCase,
    private val validatePswConfUseCase: ValidatePasswordConfirmationUseCase,
    private val validateEmptyTextUseCase: ValidateEmptyTextUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val getUnitsUseCase: GetUnitsUseCase,
    private val preferencesDataSource: PreferencesDataSource
) : ViewModel(), EventFlow<RegisterViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    private fun onClickConfirm() = viewModelScope.launch {
        uiState.isLoading.value = true
        val nameResult = validateEmptyTextUseCase(text = uiState.name.value)
        val emailResult = submitEmailUseCase(email = uiState.email.value)
        val phoneResult = validateEmptyTextUseCase(text = uiState.phone.value)
        val passwordResult = submitPasswordUseCase(password = uiState.password.value)
        val confPasswordResult = validatePswConfUseCase(
            password = uiState.password.value,
            passwordConfirmation = uiState.confirmPassword.value
        )

        when (nameResult) {
            is Resource.Error -> uiState.nameError.value = nameResult.message
            is Resource.Success -> uiState.nameError.value = null
        }

        when (emailResult) {
            is Resource.Error -> uiState.emailError.value = emailResult.message
            is Resource.Success -> uiState.emailError.value = null
        }

        when (phoneResult) {
            is Resource.Error -> uiState.phoneError.value = phoneResult.message
            is Resource.Success -> uiState.phoneError.value = null
        }

        when (passwordResult) {
            is Resource.Error -> uiState.passwordError.value = passwordResult.message
            is Resource.Success -> uiState.passwordError.value = null
        }

        when (confPasswordResult) {
            is Resource.Error -> uiState.passwordConfError.value = confPasswordResult.message
            is Resource.Success -> uiState.passwordConfError.value = null
        }

        if (!uiState.isChecked.value) { showToast(UiText.StringResource(R.string.accept_the_terms_error)) }

        uiState.isLoading.value = false

        if (
            nameResult is Resource.Success
            && emailResult is Resource.Success
            && phoneResult is Resource.Success
            && passwordResult is Resource.Success
            && confPasswordResult is Resource.Success
            && uiState.isChecked.value
        ) {
            createUserUseCase(
                CreateUserParameters(
                    name = uiState.name.value,
                    phone = uiState.phone.value,
                    password = uiState.password.value,
                    email = uiState.email.value,
                )
            ).also { result ->
                when (result) {
                    is Resource.Success -> handleSuccess(result.data!!)
                    is Resource.Error -> showToast(result.message)
                }
            }
        }
    }

    private fun handleSuccess(loginResponse: LoginResponse) = viewModelScope.launch {
        preferencesDataSource.setPref(ACCESS_TOKEN, loginResponse.accessToken)
        getUnitsUseCase(true)
        viewModelScope.sendEvent(ScreenEvent.MainScreen)
    }

    private fun handlePhoneChange(fieldsTexts: FieldsTexts.Phone): String {
        return if (fieldsTexts.text.length < BR_PHONE_LENGTH) {
            fieldsTexts.text
        } else {
            uiState.phone.value
        }
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    private fun onClickTogglePassword() {
        uiState.isPasswordVisible.value = !uiState.isPasswordVisible.value
    }


    private fun onClickTogglePasswordConf() {
        uiState.isPasswordConfirmVisible.value = !uiState.isPasswordConfirmVisible.value
    }

    private fun onCheckedChanged(isChecked: Boolean) {
        uiState.isChecked.value = isChecked
    }

    private fun onTextChanged(fieldsTexts: FieldsTexts) = when (fieldsTexts) {
        is FieldsTexts.Name -> uiState.name.value = fieldsTexts.text
        is FieldsTexts.Email -> uiState.email.value = fieldsTexts.text
        is FieldsTexts.Phone -> uiState.phone.value = handlePhoneChange(fieldsTexts)
        is FieldsTexts.Password -> uiState.password.value = fieldsTexts.text
        is FieldsTexts.ConfirmPassword -> uiState.confirmPassword.value = fieldsTexts.text
    }

    sealed class FieldsTexts {
        data class Name(val text: String) : FieldsTexts()
        data class Email(val text: String) : FieldsTexts()
        data class Phone(val text: String) : FieldsTexts()
        data class Password(val text: String) : FieldsTexts()
        data class ConfirmPassword(val text: String) : FieldsTexts()
    }

    fun onAction(action: ScreenActions) = when (action) {
        is ScreenActions.OnClickConfirm -> onClickConfirm()
        is ScreenActions.OnTextChanged -> onTextChanged(action.fieldsTexts)
        is ScreenActions.OnClickTogglePassword -> onClickTogglePassword()
        is ScreenActions.OnClickTogglePasswordConf -> onClickTogglePasswordConf()
        is ScreenActions.OnCheckChanged -> onCheckedChanged(action.isChecked)
        ScreenActions.OnClickTerms -> viewModelScope.sendEvent(ScreenEvent.NavigateTerms)
    }

    sealed class ScreenEvent {
        object MainScreen : ScreenEvent()
        object NavigateTerms : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickConfirm : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
        data class OnCheckChanged(val isChecked: Boolean) : ScreenActions()
        object OnClickTogglePassword : ScreenActions()
        object OnClickTerms : ScreenActions()
        object OnClickTogglePasswordConf : ScreenActions()
    }

    class UIState {
        val name = MutableStateFlow("")
        val email = MutableStateFlow("")
        val phone = MutableStateFlow("")
        val password = MutableStateFlow("")
        val confirmPassword = MutableStateFlow("")
        val isPasswordVisible = MutableStateFlow(false)
        val isPasswordConfirmVisible = MutableStateFlow(false)
        val nameError = MutableStateFlow<UiText?>(null)
        val emailError = MutableStateFlow<UiText?>(null)
        val phoneError = MutableStateFlow<UiText?>(null)
        val passwordError = MutableStateFlow<UiText?>(null)
        val passwordConfError = MutableStateFlow<UiText?>(null)
        val isLoading = MutableStateFlow(false)
        val isChecked = MutableStateFlow(true)
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

