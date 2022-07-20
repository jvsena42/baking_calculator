package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SuppliesViewModel @Inject constructor(
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase
) : ViewModel(), EventFlow<SuppliesViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup() {
        val suppliesResult = getAllSuppliesUseCase()
        when(suppliesResult) {
            is Resource.Error -> showToast(suppliesResult.message)
            is Resource.Success -> uiState.suppliesList.value = suppliesResult.data!!
        }
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
        val suppliesList = MutableStateFlow<List<SupplyResponse>>(emptyList())
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

