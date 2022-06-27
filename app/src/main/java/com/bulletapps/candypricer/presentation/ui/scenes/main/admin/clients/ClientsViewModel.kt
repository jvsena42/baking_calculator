package com.bulletapps.candypricer.presentation.ui.scenes.main.admin.clients

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.domain.model.User
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor() : ViewModel(), EventFlow<ClientsViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        uiState.clients.value = listOf(
            User(
                name = "Maria Júlia",
                expirationDate = Calendar.getInstance(),
                phone = "86998006407"
            ),
            User(
                name = "Ana Maria Braga",
                expirationDate = Calendar.getInstance(),
                phone = "86998006407"
            ),
            User(
                name = "Pequeno Chef",
                expirationDate = Calendar.getInstance(),
                phone = "86998006407"
            ),
            User(
                name = "Teste da silva",
                expirationDate = Calendar.getInstance(),
                phone = "86998006407"
            ),
        )
    }

    private fun onClickMessage() {

    }

    private fun changeExpirationDate() {

    }

    private fun onShowDialog() {
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
        is ScreenActions.OnClickMessage -> onClickMessage()
        is ScreenActions.OnClickChangeExpirationDate -> onShowDialog()
    }

    sealed class ScreenEvent {
        object MainScreen : ScreenEvent()
        object RegisterScreen : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickMessage : ScreenActions()
        object OnClickChangeExpirationDate : ScreenActions()
        data class OnTextChanged(val fieldsTexts: FieldsTexts) : ScreenActions()
    }

    class UIState {
        val date = MutableStateFlow("")
        val isDialogVisible = MutableStateFlow(false)
        val clients = MutableStateFlow<List<User>>(listOf())
    }
}

