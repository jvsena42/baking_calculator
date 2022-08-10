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
class ProductDetailViewModel @Inject constructor( ) : ViewModel(), EventFlow<ProductDetailViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = UIState()
    private val emptySupply = SupplyResponse(id = -1, name = "", quantity = ZERO_DOUBLE, value = ZERO_DOUBLE, null)

    suspend fun setup(product: ProductResponse?) {
        product?.let { uiState.product.value = it }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickDelete -> {}
        is ScreenActions.OnClickEdit -> {}
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
    }

    sealed class ScreenActions {
        object OnClickDelete : ScreenActions()
        object OnClickEdit : ScreenActions()
    }

    class UIState {
        val selectedSupplies = MutableStateFlow(mutableListOf<MenuItemModel>())
        val product = MutableStateFlow(
            ProductResponse(
                "", null, ZERO_DOUBLE, ZERO_DOUBLE, ZERO_DOUBLE, ZERO_DOUBLE, ZERO_DOUBLE,
                emptyList()
            )
        )
    }

    data class MenuItemModel(
        val id: Int,
        val name: String,
        val qut: Int
    )
}

