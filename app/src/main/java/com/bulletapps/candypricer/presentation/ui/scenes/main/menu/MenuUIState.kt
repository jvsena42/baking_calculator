package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.menu.MenuViewModel.*
import kotlinx.coroutines.flow.MutableStateFlow

class MenuUIState(
    val screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading),
    val menuList: MutableStateFlow<List<MenuModel>> = MutableStateFlow(emptyList()),
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {

    fun onSuccess(menuItems: List<MenuModel>) {
        menuList.value = menuItems
        isLoading.value = false
        screenState.value = ScreenState.ShowScreen
    }

    fun onFailure(retry: ScreenActions, logout: ScreenActions) {
        isLoading.value = false
        screenState.value = ScreenState.Failure(retry, logout)
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        object ShowScreen : ScreenState()
        data class Failure(val retry: ScreenActions, val logout: ScreenActions): ScreenState()
    }
}