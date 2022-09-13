package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.parameters.UpdateExpirationDateParameters
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.usecase.user.GetUsersUseCase
import com.bulletapps.candypricer.domain.usecase.user.UpdateExpirationDateUseCase
import com.bulletapps.candypricer.presentation.ui.widgets.DatePicker
import com.bulletapps.candypricer.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val updateExpirationDateUseCase: UpdateExpirationDateUseCase
) : ViewModel(), EventFlow<ClientsViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        getUsersUseCase().also {
            when (it) {
                is Resource.Error -> {}
                is Resource.Success -> uiState.clients.value = it.data.orEmpty().sortedBy { it2 -> it2.name }
            }
        }
    }

    private fun onClickMessage(phone: String) {
        viewModelScope.sendEvent(ScreenEvent.OpenWhatsApp(phone))
    }

    private fun changeExpirationDate(response: DatePicker.Result) = viewModelScope.launch {
        val selectedUser = uiState.selectedUser.value
        if (!selectedUser.id.isNegative()) {
            updateExpirationDateUseCase(selectedUser.id, UpdateExpirationDateParameters(response.date)).also {
                when(it) {
                    is Resource.Error -> showToast(it.message)
                    is Resource.Success -> getUsersUseCase()
                }
                onDismissDialog()
            }
        }

    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    private fun onShowDialog(selectedUser: UserResponse) {
        uiState.selectedUser.value = selectedUser
        uiState.expirationDateTime.value = selectedUser.expirationDate.toDate().time
        uiState.isDialogVisible.value = true
    }

    private fun onDismissDialog() {
        uiState.isDialogVisible.value = false
        uiState.selectedUser.value = UserResponse(id = NEGATIVE, name = "", email = "", phone = "", isAdmin = false, expirationDate = "", isActive = true)
        uiState.expirationDateTime.value = ZERO_LONG
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
        val expirationDateTime = MutableStateFlow(ZERO_LONG)
        val selectedUser = MutableStateFlow(UserResponse(id = NEGATIVE, name = "", email = "", phone = "", isAdmin = false, expirationDate = "", isActive = true))
        val clients = MutableStateFlow<List<UserResponse>>(listOf())
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

