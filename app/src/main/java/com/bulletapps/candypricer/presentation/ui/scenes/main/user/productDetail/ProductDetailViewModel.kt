package com.bulletapps.candypricer.presentation.ui.scenes.main.user.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.data.parameters.CreateProductParameters
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyListUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.ValidateEmptyTextUseCase
import com.bulletapps.candypricer.domain.usecase.product.CreateProductUseCase
import com.bulletapps.candypricer.domain.usecase.product.DeleteProductUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.addProduct.AddProductViewModel.*
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.ZERO_DOUBLE
import com.bulletapps.candypricer.presentation.util.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel(), EventFlow<ProductDetailViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()

    suspend fun setup(product: ProductResponse?) {
        product?.let { uiState.product.value = it }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickDelete -> deleteProduct()
        is ScreenActions.OnClickEdit -> viewModelScope.sendEvent(ScreenEvent.NavigateToAddProduct)
    }

    private fun deleteProduct() = viewModelScope.launch {
        deleteProductUseCase(uiState.product.value.id).also {
            when(it) {
                is Resource.Error -> viewModelScope.sendEvent(ScreenEvent.GoBack)
                is Resource.Success -> viewModelScope.sendEvent(ScreenEvent.GoBack)
            }
        }
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
            )
        )
    }

}

