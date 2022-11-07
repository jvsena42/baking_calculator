package com.bulletapps.candypricer.presentation.ui.scenes.main.user.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.model.SupplyModel
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
        getAllSuppliesUseCase().fold(
            onSuccess = { handleSuccess(it) },
            onFailure = { uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout) }
        )
    }

    private suspend fun handleSuccess(suppliesList: List<SupplyModel>) {
        if (suppliesList.isEmpty()) {
            viewModelScope.sendEvent(ScreenEvent.NavigateToSupplies)
        } else {
            getProducts()
        }
    }

    private suspend fun getProducts() {
        getAllProductsUseCase().fold(
            onSuccess = { uiState.onSuccess(it) },
            onFailure = { uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout) }
        )
    }

    fun onAction(action: ScreenActions) = when (action) {
        is ScreenActions.OnClickAdd -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddProduct)
        is ScreenActions.OnClickProduct -> viewModelScope.sendEvent(
            ScreenEvent.NavigateToProductDetail(
                action.product
            )
        )
        is ScreenActions.OnLogout -> onClickLogout()
        is ScreenActions.OnRetry -> setup()
    }

    private fun onClickLogout() = viewModelScope.launch {
        preferencesDataSource.clearPref()
        sendEvent(ScreenEvent.Login)
    }

    sealed class ScreenActions {
        object OnRetry : ScreenActions()
        object OnLogout : ScreenActions()
        object OnClickAdd : ScreenActions()
        data class OnClickProduct(val product: ProductModel) : ScreenActions()
    }

    sealed class ScreenEvent {
        object Login : ScreenEvent()
        object NavigateToAddProduct : ScreenEvent()
        object NavigateToSupplies : ScreenEvent()
        data class NavigateToProductDetail(val product: ProductModel) : ScreenEvent()
    }
}

