package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SupplyDetailViewModel @Inject constructor(
) : ViewModel(), EventFlow<SupplyDetailViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        uiState.supply.value = SupplyResponse(
            0,
            "Leite condensado",
            1,
            5.89,
            UnitResponse(0,"und")
        )
    }

    fun onAction(action: ScreenActions) = when(action) {
        ScreenActions.OnClickAdd -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddSupply)
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    sealed class ScreenActions {
        object OnClickAdd : ScreenActions()
    }


    sealed class ScreenEvent {
        object NavigateToAddSupply : ScreenEvent()
    }

    class UIState {
        val supply = MutableStateFlow(SupplyResponse(0,"",0,0.0, UnitResponse(0,"")))
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}
