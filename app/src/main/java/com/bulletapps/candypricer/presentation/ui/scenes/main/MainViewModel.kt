package com.bulletapps.candypricer.presentation.ui.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(),
    EventFlow<MainViewModel.Navigation> by EventFlowImpl() {

    val isLoading = MutableStateFlow(true)
    var selectedSupply: SupplyModel? = null
        private set
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

    fun saveSupply(supplyModel: SupplyModel) {
        selectedSupply = supplyModel
    }

    fun saveProduct(productModel: ProductModel) {
        selectedProduct = productModel
    }

    fun resetSupply() {
        selectedSupply = null
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

