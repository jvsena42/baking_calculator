package com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.parameters.CreateProductParameters
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.model.MenuItemModel
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyListUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyTextUseCase
import com.bulletapps.candypricer.domain.usecase.product.CreateProductUseCase
import com.bulletapps.candypricer.domain.usecase.product.DeleteProductUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.menu.MenuViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.AddProductViewModel.*
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

    fun setup(product: ProductResponse?) {
        product?.let {
            uiState.product.value = it
            uiState.supplyList.value = it.supplies.toItemMenuList(it.amountQuantitySupply)
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickDelete -> deleteProduct()
        is ScreenActions.OnClickEdit -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddProduct)
    }

    private fun deleteProduct() = viewModelScope.launch {
        deleteProductUseCase(uiState.product.value.id).also {
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
        object NavigateToAddProduct : ScreenEvent()
    }

    class UIState {
        val textToast = MutableStateFlow<UiText>(UiText.DynamicString(""))
        val supplyList = MutableStateFlow<List<MenuItemModel>>(emptyList())
        val product = MutableStateFlow(
            ProductResponse(-1,
                "",
                null,
                ZERO_DOUBLE,
                ZERO_DOUBLE,
                ZERO_DOUBLE,
                ZERO_DOUBLE,
                listOf(),
                ZERO_DOUBLE,
                ZERO_DOUBLE,
                ZERO_DOUBLE,
                emptyList()
            )
        )
    }

}

