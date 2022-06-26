package com.bulletapps.candypricer.presentation.ui.scenes.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel(), EventFlow<SettingsViewModel.ScreenEvent> by EventFlowImpl() {

    private fun onClickConfirm() {
        viewModelScope.sendEvent(ScreenEvent.Login)
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickLogout -> onClickConfirm()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        object Login : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickLogout : ScreenActions()
    }
}

