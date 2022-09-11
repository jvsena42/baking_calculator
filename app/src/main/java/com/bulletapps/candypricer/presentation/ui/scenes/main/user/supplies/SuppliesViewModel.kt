package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ProductsViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuppliesViewModel @Inject constructor(
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase,
    private val preferencesDataSource: PreferencesDataSource,
    ) : ViewModel(), EventFlow<SuppliesViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = SuppliesUIState()

    suspend fun setup() {
        getAllSuppliesUseCase().also {
            when(it) {
                is Resource.Error -> uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout)
                is Resource.Success -> uiState.onSuccess(it.data.orEmpty())
            }
        }

    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickAdd -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddSupply)
        is ScreenActions.OnClickSupply -> viewModelScope.sendEvent(ScreenEvent.NavigateSupplyDetail(action.supply))
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
        data class  OnClickSupply(val supply: SupplyResponse) : ScreenActions()
    }


    sealed class ScreenEvent {
        object Login : ScreenEvent()
        object NavigateToAddSupply : ScreenEvent()
        data class NavigateSupplyDetail(val supply: SupplyResponse) : ScreenEvent()
    }
}

