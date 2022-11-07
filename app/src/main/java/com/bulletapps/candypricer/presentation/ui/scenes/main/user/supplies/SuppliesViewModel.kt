package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuppliesViewModel @Inject constructor(
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase,
    private val preferencesDataSource: PreferencesDataSource,
) : ViewModel(), EventFlow<SuppliesViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = SuppliesUIState()

    fun setup() = viewModelScope.launch {
        getAllSuppliesUseCase().fold(
            onSuccess = { uiState.onSuccess(it) },
            onFailure = { uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout) }
        )
    }

    fun onAction(action: ScreenActions) = when (action) {
        is ScreenActions.OnClickAdd -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddSupply)
        is ScreenActions.OnClickSupply -> viewModelScope.sendEvent(
            ScreenEvent.NavigateSupplyDetail(
                action.supply
            )
        )
        is ScreenActions.OnLogout -> onClickLogout()
        is ScreenActions.OnRetry -> viewModelScope.launch { setup() }
    }

    private fun onClickLogout() = viewModelScope.launch {
        preferencesDataSource.clearPref()
        sendEvent(ScreenEvent.Login)
    }

    sealed class ScreenActions {
        object OnRetry : ScreenActions()
        object OnLogout : ScreenActions()
        object OnClickAdd : ScreenActions()
        data class OnClickSupply(val supply: SupplyModel) : ScreenActions()
    }

    sealed class ScreenEvent {
        object Login : ScreenEvent()
        object NavigateToAddSupply : ScreenEvent()
        data class NavigateSupplyDetail(val supply: SupplyModel) : ScreenEvent()
    }
}

