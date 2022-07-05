package com.bulletapps.candypricer.presentation.ui.scenes.main.user.expired

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.domain.model.User
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpiredViewModel @Inject constructor() : ViewModel(), EventFlow<ExpiredViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    private fun onClickMessage() {
        viewModelScope.sendEvent(ScreenEvent.OpenWhatsApp("+5586981133033"))
    }

    private fun onClickLogout() {

    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickMessage -> onClickMessage()
        is ScreenActions.OnClickLogout -> onClickLogout()
    }

    sealed class ScreenEvent {
        data class OpenWhatsApp(val number: String) : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickMessage : ScreenActions()
        object OnClickLogout : ScreenActions()
    }

    class UIState {
    }
}

