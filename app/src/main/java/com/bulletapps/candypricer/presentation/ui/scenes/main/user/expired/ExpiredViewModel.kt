package com.bulletapps.candypricer.presentation.ui.scenes.main.user.expired

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.domain.model.User
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings.SettingsViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.WHATSAPP_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpiredViewModel @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,

    ) : ViewModel(), EventFlow<ExpiredViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    private fun onClickMessage() {
        viewModelScope.sendEvent(ScreenEvent.OpenWhatsApp(WHATSAPP_NUMBER))
    }

    private fun onClickLogout() {
        preferencesDataSource.clearPref()
        viewModelScope.sendEvent(ScreenEvent.Login)
    }

    private fun onDelete() = viewModelScope.launch {
        onClickLogout()
    }

    private fun handleDialogVisibility(shouldShow: Boolean) = viewModelScope.launch {
        uiState.isDialogVisible.value = shouldShow
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickMessage -> onClickMessage()
        is ScreenActions.OnClickLogout -> onClickLogout()
        is ScreenActions.OnClickDelete -> handleDialogVisibility(true)
        is ScreenActions.OnDismissDialog -> handleDialogVisibility(false)
        is ScreenActions.OnConfirmDelete -> onDelete()
    }

    sealed class ScreenEvent {
        data class OpenWhatsApp(val number: String) : ScreenEvent()
        object Login : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickMessage : ScreenActions()
        object OnClickLogout : ScreenActions()
        object OnClickDelete : ScreenActions()
        object OnConfirmDelete : ScreenActions()
        object OnDismissDialog : ScreenActions()
    }

    class UIState {
        val isDialogVisible = MutableStateFlow(false)
    }
}

