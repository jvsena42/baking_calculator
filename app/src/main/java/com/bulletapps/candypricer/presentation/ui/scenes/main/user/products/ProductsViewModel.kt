package com.bulletapps.candypricer.presentation.ui.scenes.main.user.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.domain.usecase.product.GetAllProductsUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getAllSuppliesUseCase: GetAllSuppliesUseCase,
    private val preferencesDataSource: PreferencesDataSource,
    ) : ViewModel(), EventFlow<ProductsViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = ProductsUIState()

    fun setup() = viewModelScope.launch {

        val supplyResult = getAllSuppliesUseCase()
        when(supplyResult) {
            is Resource.Error -> uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout)
            is Resource.Success -> {
                if (supplyResult.data.isNullOrEmpty()) {
                    sendEvent(ScreenEvent.NavigateToSupplies)
                } else {
                    getProducts()
                }
            }
        }

    }

    private suspend fun getProducts() {
        val productsResult = getAllProductsUseCase()
        when (productsResult) {
            is Resource.Error -> uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout)
            is Resource.Success -> uiState.onSuccess(productsResult.data.orEmpty())
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickAdd -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddProduct)
        is ScreenActions.OnClickProduct -> viewModelScope.sendEvent(ScreenEvent.NavigateToProductDetail(action.product))
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
        data class OnClickProduct(val product: ProductResponse) : ScreenActions()
    }

    sealed class ScreenEvent {
        object Login : ScreenEvent()
        object NavigateToAddProduct : ScreenEvent()
        object NavigateToSupplies : ScreenEvent()
        data class NavigateToProductDetail(val product: ProductResponse) : ScreenEvent()
    }
}

