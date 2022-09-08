package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.UpdateUserParameters
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.usecase.user.GetUsersUseCase
import com.bulletapps.candypricer.domain.usecase.user.UpdateUserUseCase
import com.bulletapps.candypricer.presentation.ui.widgets.DatePicker
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.NEGATIVE
import com.bulletapps.candypricer.presentation.util.isNegative
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel(), EventFlow<ClientsViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        val result = getUsersUseCase()
        when (result) {
            is Resource.Error -> {}
            is Resource.Success ->  uiState.clients.value = result.data.orEmpty()
        }
    }

    private fun onClickMessage(phone: String) {
        viewModelScope.sendEvent(ScreenEvent.OpenWhatsApp(phone))
    }

    private fun changeExpirationDate(response: DatePicker.Result) = viewModelScope.launch {
        val selectedUser = uiState.selectedUser.value
        if (!selectedUser.id.isNegative()) {
            val updatedUser = UpdateUserParameters(
                selectedUser.id,
                selectedUser.name,
                selectedUser.phone,
                selectedUser.email,
                selectedUser.isAdmin,
                response.date
            )
            val result = updateUserUseCase(updatedUser)
            if (result is Resource.Success) getUsersUseCase()
            onDismissDialog()
        }

    }

    private fun onShowDialog(selectedUser: UserResponse) {
        uiState.selectedUser.value = selectedUser
        uiState.isDialogVisible.value = true
    }

    private fun onDismissDialog() {
        uiState.isDialogVisible.value = false
        uiState.selectedUser.value = UserResponse(id = NEGATIVE, name = "", email = "", phone = "", isAdmin = false, expirationDate = "")
    }

    private fun onTextChanged(fieldsTexts: FieldsTexts) = when(fieldsTexts) {
        is FieldsTexts.Date -> uiState.date.value = fieldsTexts.text
    }

    sealed class FieldsTexts {
        data class Date(val text: String) : FieldsTexts()
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnTextChanged -> onTextChanged(action.fieldsTexts)
        is ScreenActions.OnClickMessage -> onClickMessage(action.phone)
        is ScreenActions.OnClickChangeExpirationDate -> onShowDialog(action.user)
        is ScreenActions.OnDismissDialog -> onDismissDialog()
        is ScreenActions.OnConfirmDate -> changeExpirationDate(action.response)
    }

    sealed class ScreenEvent {
        data class OpenWhatsApp(val number: String) : ScreenEvent()
    }

    sealed class ScreenActions {
        data class OnClickMessage(val phone: String) : ScreenActions()
        data class OnClickChangeExpirationDate(val user: UserResponse) : ScreenActions()
        data class OnConfirmDate(val response: DatePicker.Result) : ScreenActions()
        object OnDismissDialog : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
    }

    class UIState {
        val date = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
        val selectedUser = MutableStateFlow(UserResponse(id = NEGATIVE, name = "", email = "", phone = "", isAdmin = false, expirationDate = ""))
        val clients = MutableStateFlow<List<UserResponse>>(listOf())
    }
}

