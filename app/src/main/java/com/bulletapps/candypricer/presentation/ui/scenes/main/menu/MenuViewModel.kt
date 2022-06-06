package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {

    val menuItems: MutableStateFlow<List<MenuModel>> = MutableStateFlow(
        listOf(
            MenuModel(R.string.my_products, R.drawable.ic_store, -1),
            MenuModel(R.string.supplies, R.drawable.ic_shopping_cart, -1),
            MenuModel(R.string.settings, R.drawable.ic_build, -1),
        ),
    )

}

