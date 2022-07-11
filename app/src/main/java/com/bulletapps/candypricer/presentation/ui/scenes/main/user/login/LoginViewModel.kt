package com.bulletapps.candypricer.presentation.ui.scenes.main.user.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.parameters.LoginParameters
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitEmailUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitPasswordUseCase
import com.bulletapps.candypricer.domain.usecase.user.LoginUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.register.RegisterViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val submitEmailUseCase: SubmitEmailUseCase,
    private val submitPasswordUseCase: SubmitPasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel(), EventFlow<LoginViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    private fun onClickConfirm() {
        viewModelScope.launch {
            uiState.isLoading.value = true
            val emailResult = submitEmailUseCase(email = uiState.email.value)
            val passwordResult = submitPasswordUseCase(password = uiState.password.value)

            when(emailResult) {
                is Resource.Error -> uiState.emailError.value = emailResult.message
                is Resource.Success -> uiState.emailError.value = null
            }

            when(passwordResult) {
                is Resource.Error -> uiState.passwordError.value = passwordResult.message
                is Resource.Success -> uiState.passwordError.value = null
            }

            uiState.isLoading.value = false
            if (
                emailResult is Resource.Success
                && passwordResult is Resource.Success
            ) {
                loginUseCase(
                    LoginParameters(
                        email = uiState.email.value,
                        password = uiState.password.value
                    )
                ).also { result ->
                    when (result) {
                        is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.MainScreen)
                        is Resource.Error -> showToast(result.message?.asString())
                    }
                }
            }
        }
    }

    private fun showToast(message: String?) {
        message?.let{ uiState.textToast.value = it }
    }

    private fun onCLickRegister() {
        viewModelScope.sendEvent(ScreenEvent.RegisterScreen)
    }

    private fun onTextChanged(fieldsTexts: FieldsTexts) = when(fieldsTexts) {
        is FieldsTexts.Email -> uiState.email.value = fieldsTexts.text
        is FieldsTexts.Password -> uiState.password.value = fieldsTexts.text
    }

    private fun onClickTogglePassword() {
        uiState.isPasswordVisible.value = !uiState.isPasswordVisible.value
    }

    sealed class FieldsTexts {
        data class Email(val text: String) : FieldsTexts()
        data class Password(val text: String) : FieldsTexts()
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickConfirm -> onClickConfirm()
        is ScreenActions.OnTextChanged -> onTextChanged(action.fieldsTexts)
        is ScreenActions.OnClickRegister -> onCLickRegister()
        is ScreenActions.OnClickTogglePassword -> onClickTogglePassword()
    }

    sealed class ScreenEvent {
        object MainScreen : ScreenEvent()
        object RegisterScreen : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickConfirm : ScreenActions()
        object OnClickRegister : ScreenActions()
        object OnClickTogglePassword : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
    }

    class UIState {
        val email = MutableStateFlow("")
        val password = MutableStateFlow("")
        val isPasswordVisible = MutableStateFlow(false)
        val emailError = MutableStateFlow<UiText?>(null)
        val passwordError = MutableStateFlow<UiText?>(null)
        val isLoading = MutableStateFlow(false)
        val textToast = MutableStateFlow("")
    }
}

