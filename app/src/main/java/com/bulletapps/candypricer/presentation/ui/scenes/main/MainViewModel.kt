package com.bulletapps.candypricer.presentation.ui.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel(), EventFlow<MainViewModel.Navigation> by EventFlowImpl()  {

    val menuItems: MutableStateFlow<List<MenuModel>> = MutableStateFlow(
        listOf(
            MenuModel(R.string.my_products, R.drawable.ic_store, Navigation.MyProducts),
            MenuModel(R.string.supplies, R.drawable.ic_shopping_cart, Navigation.Supplies),
            MenuModel(R.string.settings, R.drawable.ic_build, Navigation.Settings),
        ),
    )

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    sealed class Navigation(val router: String) {
        object MainMenu : Navigation("main_menu")
        object MyProducts : Navigation("my_products")
        object Supplies : Navigation("supplies")
        object AddSupply : Navigation("add_supply")
        object Settings : Navigation("settings")
    }
}

