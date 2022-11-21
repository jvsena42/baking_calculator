package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplyDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.domain.usecase.supply.DeleteSupplyUseCase
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
    private var id: Int = NEGATIVE

    fun setup(supplyModel: SupplyModel?) = viewModelScope.launch {
        supplyModel?.run {
            uiState.supplyName.value = supplyModel.name
            uiState.supplyQuantity.value = supplyModel.quantity.round()
            uiState.supplyUnitName.value = supplyModel.unit.label.formatUnit()
            uiState.supplyPrice.value = supplyModel.price.toCurrency()
        }

    }

    fun onAction(action: ScreenActions) = when (action) {
        ScreenActions.OnCLickEdit -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddSupply)
        ScreenActions.OnCLickDelete -> deleteSupply()
    }

    private fun deleteSupply() = viewModelScope.launch {
        if (id.isNegative()) return@launch
        deleteSupplyUseCase(id).also {
            when (it) {
                is Resource.Error -> showToast(it.message)
                is Resource.Success -> sendEvent(ScreenEvent.PopScreen)
            }
        }
    }

    private fun showToast(message: UiText?) {
        message?.let { uiState.textToast.value = it }
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
        val supplyName = MutableStateFlow("")
        val supplyQuantity = MutableStateFlow("")
        val supplyUnitName = MutableStateFlow("")
        val supplyPrice = MutableStateFlow("")
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

