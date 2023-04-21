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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyPlanViewModel @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val deleteUserUseCase: DeleteUserUseCase
    ) : ViewModel(), EventFlow<BuyPlanViewModel.ScreenEvent> by EventFlowImpl() {

    private val _uiState = MutableStateFlow(BuyPlanUIState())
    val uiState = _uiState.asStateFlow()

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

    private fun showToast(message: String) {
        _uiState.update {
            it.copy(
                textToast = message
            )
        }
    }

    private fun handleDialogVisibility(shouldShow: Boolean) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                isDialogVisible = shouldShow
            )
        }
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
}
