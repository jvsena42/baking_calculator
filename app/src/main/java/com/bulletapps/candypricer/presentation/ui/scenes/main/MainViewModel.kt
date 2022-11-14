package com.bulletapps.candypricer.presentation.ui.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.menu.SupplyUIState
import com.bulletapps.candypricer.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(),
    EventFlow<MainViewModel.Navigation> by EventFlowImpl() {

    val supplyUIState = SupplyUIState()

    val isLoading = MutableStateFlow(true)
    var selectedProduct: ProductModel? = null
        private set

    val menuItems: MutableStateFlow<List<MenuModel>> = MutableStateFlow(emptyList())

    init {
        setIsLoading()
    }

    private fun setIsLoading() = viewModelScope.launch {
        delay(2000L)
        isLoading.value = false
    }

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    fun saveSupply(supplyModel: SupplyModel) = supplyModel.run {
        supplyUIState.id.value = id
        supplyUIState.supplyName.value = name
        supplyUIState.supplyQuantity.value = quantity.round()
        supplyUIState.supplyUnitName.value = unit.label.formatUnit()
        supplyUIState.supplyPrice.value = price.toCurrency()
    }

    fun saveProduct(productModel: ProductModel) {
        selectedProduct = productModel
    }

    fun resetSupply() {
        supplyUIState.id.value = NEGATIVE
    }

    fun resetProduct() {
        selectedProduct = null
    }

    sealed class Navigation(
        val router: String,
        val shouldPop: Boolean = false,
        val popHome: Boolean = false
    ) {
        object MainMenu : Navigation("main_menu", true)
        object Products : Navigation("products")
        object ProductDetail : Navigation("product_detail")
        object AddProduct : Navigation("add_product")
        object Supplies : Navigation("supplies", popHome = true)
        object SupplyDetail : Navigation("supply_detail")
        object AddSupply : Navigation("add_supply")
        object Settings : Navigation("settings")
        object Login : Navigation("login", true)
        object Register : Navigation("register")
        object Clients : Navigation("clients")
        object Expired : Navigation("expired", true)
    }
}

