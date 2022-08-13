package com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.menu.MenuViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val getUserUseCase: GetUserUseCase,
    ) : ViewModel(), EventFlow<SettingsViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        val result = getUserUseCase()

        if (result is Resource.Success) {
            val user = result.data
            uiState.name.value = user?.name.orEmpty()
            uiState.email.value = user?.email.orEmpty()
            uiState.phone.value = user?.phone.orEmpty() //TODO format
            uiState.expirationDate.value = user?.expirationDate.orEmpty() //TODO format
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickLogout -> onClickLogout()
        is ScreenActions.OnClickUpdate -> onClickUpdate()
    }

    private fun onClickUpdate() = viewModelScope.launch {
        //TODO IMPLEMENT
    }

    private fun onClickLogout() = viewModelScope.launch {
        preferencesDataSource.clearPref()
        sendEvent(ScreenEvent.Login)
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        object Login : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickLogout : ScreenActions()
        object OnClickUpdate : ScreenActions()
    }

    class UIState {
        val name = MutableStateFlow("")
        val email = MutableStateFlow("")
        val phone = MutableStateFlow("")
        val expirationDate = MutableStateFlow("")
    }

}

