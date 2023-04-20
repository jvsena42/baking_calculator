package com.bulletapps.candypricer.presentation.ui.scenes.main.user.buyPlan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.domain.usecase.user.DeleteUserUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.WHATSAPP_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyPlanViewModel @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val deleteUserUseCase: DeleteUserUseCase
    ) : ViewModel(), EventFlow<BuyPlanViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    private fun onClickLogout() {
        preferencesDataSource.clearPref()
        viewModelScope.sendEvent(ScreenEvent.Login)
    }

    private fun onDelete() = viewModelScope.launch {
        deleteUserUseCase().also {
            when (it) {
                is Resource.Error -> showToast(it.message)
                is Resource.Success -> onClickLogout()
            }
        }
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    private fun handleDialogVisibility(shouldShow: Boolean) = viewModelScope.launch {
        uiState.isDialogVisible.value = shouldShow
    }

    fun onAction(action: ScreenActions) {
        when (action) {
            is ScreenActions.OnClickLogout -> onClickLogout()
            is ScreenActions.OnClickDelete -> handleDialogVisibility(true)
            is ScreenActions.OnDismissDialog -> handleDialogVisibility(false)
            is ScreenActions.OnConfirmDelete -> onDelete()
        }
    }

    sealed class ScreenEvent {
        data class OpenWhatsApp(val number: String) : ScreenEvent()
        object Login : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickLogout : ScreenActions()
        object OnClickDelete : ScreenActions()
        object OnConfirmDelete : ScreenActions()
        object OnDismissDialog : ScreenActions()
    }

    class UIState {
        val isDialogVisible = MutableStateFlow(false)
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

