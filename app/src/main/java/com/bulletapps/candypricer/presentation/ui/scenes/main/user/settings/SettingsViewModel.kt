package com.bulletapps.candypricer.presentation.ui.scenes.main.user.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.BuildConfig
import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.domain.model.UserModel
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.presentation.util.*
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

    fun setup() = viewModelScope.launch {
        getUserUseCase().fold(
            onSuccess = { handleSuccess(it) },
            onFailure = {  } //TODO Implement error screen
        )
    }

    private fun handleSuccess(user: UserModel) {
        uiState.name.value = user.name
        uiState.email.value = user.email
        uiState.phone.value =  user.phone.formatPhone()
        uiState.expirationDate.value = user.expirationDate.toDate().formatToDayMonthYear()
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickLogout -> onClickLogout()
        is ScreenActions.OnClickUpdate -> viewModelScope.sendEvent(ScreenEvent.OpenWhatsApp(WHATSAPP_NUMBER))
        is ScreenActions.OnClickLink -> onClickLink()
    }

    private fun onClickLink() = viewModelScope.launch {
        sendEvent(ScreenEvent.NavigateLink(BuildConfig.LINKTREE_URL))
    }

    private fun onClickLogout() = viewModelScope.launch {
        preferencesDataSource.clearPref()
        sendEvent(ScreenEvent.Login)
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        object Login : ScreenEvent()
        data class NavigateLink(val url: String) : ScreenEvent()
        data class OpenWhatsApp(val number: String) : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickLogout : ScreenActions()
        object OnClickUpdate : ScreenActions()
        object OnClickLink : ScreenActions()
    }

    class UIState {
        val name = MutableStateFlow("")
        val email = MutableStateFlow("")
        val phone = MutableStateFlow("")
        val expirationDate = MutableStateFlow("")
    }
}

