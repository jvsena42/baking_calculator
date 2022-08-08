package com.bulletapps.candypricer.presentation.ui.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.MenuModel
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

    val menuItems: MutableStateFlow<List<MenuModel>> = MutableStateFlow(
        listOf(
            MenuModel(R.string.my_products, R.drawable.ic_store, Navigation.Products),
            MenuModel(R.string.supplies, R.drawable.ic_shopping_cart, Navigation.Supplies),
//            MenuModel(R.string.clients, R.drawable.ic_clients, Navigation.Clients), //todo handle clients
            MenuModel(R.string.settings, R.drawable.ic_build, Navigation.Settings),
        ),
    )

    init {
        setIsLoading()
    }

    private fun setIsLoading() = viewModelScope.launch {
        delay(3000L)
        isLoading.value = false
    }

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    sealed class Navigation(val router: String, val shouldPop: Boolean = false) {
        object MainMenu : Navigation("main_menu", true)
        object Products : Navigation("products",)
        object AddProduct : Navigation("add_product")
        object Supplies : Navigation("supplies")
        object AddSupply : Navigation("add_supply")
        object Settings : Navigation("settings")
        object Login : Navigation("login", true)
        object Register : Navigation("register")
        object Clients : Navigation("clients")
        object Expired : Navigation("expired", true)
    }
}

