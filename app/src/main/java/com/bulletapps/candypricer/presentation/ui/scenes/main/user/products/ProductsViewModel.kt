package com.bulletapps.candypricer.presentation.ui.scenes.main.user.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.domain.usecase.product.GetAllProductsUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel(), EventFlow<ProductsViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()


    suspend fun setup() {
        val productsResult = getAllProductsUseCase()
        when(productsResult) {
            is Resource.Error -> showToast(productsResult.message)
            is Resource.Success -> uiState.productsList.value = productsResult.data!!
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        ScreenActions.OnClickAdd -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddProduct)
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    sealed class ScreenActions {
        object OnClickAdd : ScreenActions()
    }

    sealed class ScreenEvent {
        object NavigateToAddProduct : ScreenEvent()
    }

    class UIState {
        val productsList = MutableStateFlow<List<ProductResponse>>(emptyList())
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
    }
}

