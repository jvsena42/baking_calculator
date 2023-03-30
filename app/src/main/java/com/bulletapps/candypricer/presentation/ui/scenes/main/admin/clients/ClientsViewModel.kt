package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.domain.model.UserModel
import com.bulletapps.candypricer.domain.usecase.user.GetUsersUseCase
import com.bulletapps.candypricer.domain.usecase.user.UpdateExpirationDateUseCase
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
        getUsersUseCase().fold(
            onSuccess = { uiState.clients.value = it.sortedBy { it2 -> it2.name } },
            onFailure = { showToast(it.message) }
        )
    }

    private fun onClickMessage(phone: String) {
        viewModelScope.sendEvent(ScreenEvent.OpenWhatsApp(phone))
    }

    private fun changeExpirationDate() = viewModelScope.launch {
        val selectedUserId = uiState.selectedUserId.value
        val currentDate = uiState.date.value
        if (selectedUserId.isPositive() && currentDate.isNotEmpty()) {
            updateExpirationDateUseCase(selectedUserId, currentDate).also {
                when(it) {
                    is Resource.Error -> showToast(it.message)
                    is Resource.Success -> setup()
                }
                onDismissDialog()
            }
        }

    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    private fun showToast(message: String?) {
        message?.let{ uiState.textToast.value = UiText.DynamicString(it) }
    }

    private fun onShowDialog(selectedUserId: Int) {
        uiState.selectedUserId.value = selectedUserId
        uiState.isDialogVisible.value = true
    }

    private fun onDismissDialog() {
        uiState.isDialogVisible.value = false
        uiState.date.value = EMPTY_STRING
        uiState.selectedUserId.value = NEGATIVE
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
        is ScreenActions.OnClickChangeExpirationDate -> onShowDialog(action.userId)
        is ScreenActions.OnDismissDialog -> onDismissDialog()
        is ScreenActions.OnConfirmDate -> changeExpirationDate()
    }


    sealed class ScreenEvent {
        data class OpenWhatsApp(val number: String) : ScreenEvent()
    }

    sealed class ScreenActions {
        data class OnClickMessage(val phone: String) : ScreenActions()
        data class OnClickChangeExpirationDate(val userId: Int) : ScreenActions()
        object OnConfirmDate : ScreenActions()
        object OnDismissDialog : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
    }

    class UIState {
        val date = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
        val selectedUserId = MutableStateFlow(NEGATIVE)
        val clients = MutableStateFlow<List<UserModel>>(listOf())
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

