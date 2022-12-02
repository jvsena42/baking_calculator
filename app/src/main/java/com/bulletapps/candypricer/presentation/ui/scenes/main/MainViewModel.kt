package com.bulletapps.candypricer.presentation.ui.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.domain.usecase.user.LogoutUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUnitsUseCase: GetUnitsUseCase,
    ) : ViewModel(),
    EventFlow<MainViewModel.Navigation> by EventFlowImpl() {

    var supplyModel: SupplyModel? = null
    var productModel: ProductModel? = null

    val isLoading = MutableStateFlow(true)

    val menuItems: MutableStateFlow<List<MenuModel>> = MutableStateFlow(emptyList())

    init {
        setIsLoading()
    }

    private fun setIsLoading() = viewModelScope.launch {
        getUnitsUseCase.invoke(isRefresh = true)
        delay(2000L)
        isLoading.value = false
    }

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
        navigate(Navigation.Login)
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
        object UpdateProduct : Navigation("update_product")
        object Supplies : Navigation("supplies", popHome = true)
        object SupplyDetail : Navigation("supply_detail")
        object AddSupply : Navigation("add_supply")
        object UpdateSupply : Navigation("update_supply")
        object Settings : Navigation("settings")
        object Login : Navigation("login", true)
        object Register : Navigation("register")
        object Clients : Navigation("clients")
        object Expired : Navigation("expired", true)
    }
}

