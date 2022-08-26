package com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies

import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.supplies.SuppliesViewModel.*
import kotlinx.coroutines.flow.MutableStateFlow

class SuppliesUIState(
    val screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading),
    val suppliesList: MutableStateFlow<List<SupplyResponse>> = MutableStateFlow(emptyList()),
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {

    fun onSuccess(supplies: List<SupplyResponse>) {
        suppliesList.value = supplies
        isLoading.value = false
        screenState.value = ScreenState.ShowScreen
    }

    fun onFailure(retry: ScreenActions, logout: ScreenActions) {
        isLoading.value = false
        screenState.value = ScreenState.Failure(retry, logout)
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        object ShowScreen : ScreenState()
        data class Failure(val retry: ScreenActions, val logout: ScreenActions): ScreenState()
    }
}