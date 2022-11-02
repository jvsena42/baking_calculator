package com.bulletapps.candypricer.presentation.ui.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel(), EventFlow<MainViewModel.Navigation> by EventFlowImpl()  {

    val isLoading = MutableStateFlow(true)
    val selectedSupply = MutableStateFlow<SupplyModel?>(null)
    val selectedProduct = MutableStateFlow<ProductResponse?>(null)

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

    sealed class Navigation(val router: String, val shouldPop: Boolean = false, val popHome: Boolean = false) {
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

