package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.model.User
import com.bulletapps.candypricer.domain.usecase.user.GetUsersUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
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

    private fun changeExpirationDate() {

    }

    private fun onShowDialog(selectedUser: UserResponse) {
        uiState.isDialogVisible.value = true
    }
    private fun onDismissDialog() {
        uiState.isDialogVisible.value = false
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
        ScreenActions.OnDismissDialog -> onDismissDialog()
    }

    sealed class ScreenEvent {
        data class OpenWhatsApp(val number: String) : ScreenEvent()
    }

    sealed class ScreenActions {
        data class OnClickMessage(val phone: String) : ScreenActions()
        data class OnClickChangeExpirationDate(val user: UserResponse) : ScreenActions()
        object OnDismissDialog : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
    }

    class UIState {
        val date = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
        val clients = MutableStateFlow<List<UserResponse>>(listOf())
    }
}

