package com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.domain.model.MenuItemModel
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.usecase.product.DeleteProductUseCase
import com.bulletapps.candypricer.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel(), EventFlow<ProductDetailViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    fun setup(product: ProductModel?) {
        product?.let {
            uiState.supplyList.value = it.supplies.toItemMenuList()
            uiState.quantity.value = it.quantity.round()
            uiState.laborValue.value = it.laborValue.toPercentString()
            uiState.variableExpenses.value = it.variableExpenses.toPercentString()
            uiState.profitMargin.value = it.profitMargin.toPercentString()
            uiState.totalSpendsValue.value = it.totalSpendsValue.toCurrency()
            uiState.unitSaleValue.value = it.unitSaleValue.toCurrency()
            uiState.unit.value = it.unit.label.formatUnit()
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickDelete -> deleteProduct()
        is ScreenActions.OnClickEdit -> viewModelScope.sendEvent(ScreenEvent.NavigateUpdateProduct)
    }

    private fun deleteProduct() = viewModelScope.launch {
        deleteProductUseCase(uiState.id.value).also {
            when(it) {
                is Resource.Error -> showToast(it.message)
                is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.GoBack)
            }
        }
    }

    private fun showToast(message: UiText?) {
        message?.let{ uiState.textToast.value = it }
    }

    sealed class ScreenActions {
        object OnClickDelete : ScreenActions()
        object OnClickEdit : ScreenActions()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        object NavigateUpdateProduct : ScreenEvent()
    }

    class UIState {
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
        val supplyList = MutableStateFlow<List<MenuItemModel>>(emptyList())
        val id = MutableStateFlow(NEGATIVE)
        val quantity = MutableStateFlow(EMPTY_STRING)
        val unit = MutableStateFlow(EMPTY_STRING)
        val laborValue = MutableStateFlow(EMPTY_STRING)
        val variableExpenses = MutableStateFlow(EMPTY_STRING)
        val profitMargin = MutableStateFlow(EMPTY_STRING)
        val totalSpendsValue = MutableStateFlow(EMPTY_STRING)
        val unitSaleValue = MutableStateFlow(EMPTY_STRING)
    }

}

