package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.usecase.supply.DeleteSupplyUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupplyDetailViewModel @Inject constructor(
    private val deleteSupplyUseCase: DeleteSupplyUseCase
) : ViewModel(), EventFlow<SupplyDetailViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup(supply: SupplyResponse?) {
        supply?.let{ uiState.supply.value = it }
    }

    fun onAction(action: ScreenActions) = when(action) {
        ScreenActions.OnCLickEdit -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddSupply)
        ScreenActions.OnCLickDelete -> deleteSupply()
    }

    private fun deleteSupply() = viewModelScope.launch {
        deleteSupplyUseCase(uiState.supply.value.id.orNegative()).also {
            when(it) {
                is Resource.Error -> showToast(it.message)
                is Resource.Success -> sendEvent(ScreenEvent.PopScreen)
            }
        }
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    sealed class ScreenActions {
        object OnCLickEdit : ScreenActions()
        object OnCLickDelete : ScreenActions()
    }


    sealed class ScreenEvent {
        object NavigateToAddSupply : ScreenEvent()
        object PopScreen : ScreenEvent()
    }

    class UIState {
        val supply = MutableStateFlow(SupplyResponse(0,"", ZERO_DOUBLE, ZERO_DOUBLE, UnitResponse(
            ZERO,"")))
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

