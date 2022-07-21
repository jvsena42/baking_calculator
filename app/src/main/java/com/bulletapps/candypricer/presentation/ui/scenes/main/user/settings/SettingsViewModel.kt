package com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : ViewModel(), EventFlow<SettingsViewModel.ScreenEvent> by EventFlowImpl() {

    private fun onClickConfirm() = viewModelScope.launch {
        preferencesDataSource.clearPref()
        sendEvent(ScreenEvent.Login)
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

