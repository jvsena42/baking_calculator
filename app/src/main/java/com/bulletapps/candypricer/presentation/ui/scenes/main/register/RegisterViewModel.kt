package com.bulletapps.candypricer.presentation.ui.scenes.main.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel(), EventFlow<RegisterViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    private fun onClickConfirm() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    private fun onTextChanged(fieldsTexts: FieldsTexts) = when(fieldsTexts) {
        is FieldsTexts.Name -> uiState.name.value = fieldsTexts.text
        is FieldsTexts.Email -> uiState.email.value = fieldsTexts.text
        is FieldsTexts.Phone -> uiState.phone.value = fieldsTexts.text
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

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickConfirm -> onClickConfirm()
        is ScreenActions.OnTextChanged -> onTextChanged(action.fieldsTexts)
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickConfirm : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
    }

    class UIState {
        val name = MutableStateFlow("")
        val email = MutableStateFlow("")
        val phone = MutableStateFlow("")
        val password = MutableStateFlow("")
        val confirmPassword = MutableStateFlow("")
    }
}

