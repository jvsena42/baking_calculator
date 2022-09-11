package com.bulletapps.candypricer.presentation.ui.scenes.main.user.products

import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ProductsViewModel.*
import kotlinx.coroutines.flow.MutableStateFlow

class ProductsUIState(
    val screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading),
    val productsList: MutableStateFlow<List<ProductResponse>> = MutableStateFlow(emptyList()),
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {

    fun onSuccess(products: List<ProductResponse>) {
        productsList.value = products.sortedBy { it.name }
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