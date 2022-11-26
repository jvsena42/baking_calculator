package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.model.UserModel
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.orFalse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val preferencesDataSource: PreferencesDataSource,
) : ViewModel(), EventFlow<MenuViewModel.ScreenEvent> by EventFlowImpl() {

    val uiState = MenuUIState()

    private val menuClient = mutableListOf(
        MenuModel(R.string.my_products, R.drawable.ic_store, MainViewModel.Navigation.Products),
        MenuModel(
            R.string.supplies,
            R.drawable.ic_shopping_cart,
            MainViewModel.Navigation.Supplies
        ),
        MenuModel(R.string.settings, R.drawable.ic_build, MainViewModel.Navigation.Settings),
    )

    private val menuAdmin = mutableListOf<MenuModel>().apply {
        addAll(menuClient)
        add(MenuModel(R.string.clients, R.drawable.ic_clients, MainViewModel.Navigation.Clients))
    }

    fun setup() = viewModelScope.launch {
        getUserUseCase().fold(
            onSuccess = {
                handleExpiredScreenVisibility(it)
                handleMenuType(it)
            },
            onFailure = {
                uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout)
            }
        )
    }

    private fun handleExpiredScreenVisibility(user: UserModel) {
        if (!user.isActive && !user.isAdmin) {
            viewModelScope.sendEvent(ScreenEvent.ExpiredScreen)
        }
    }

    private fun handleMenuType(user: UserModel) {
        if (!user.isActive && !user.isAdmin) return
        val items = if (user.isAdmin) menuAdmin else menuClient
        uiState.onSuccess(items)
    }

    fun onAction(action: ScreenActions) = when (action) {
        is ScreenActions.OnClickItem -> viewModelScope.sendEvent(ScreenEvent.Navigate(action.path))
        ScreenActions.OnLogout -> onClickLogout()
        ScreenActions.OnRetry -> setup()
    }

    private fun onClickLogout() = viewModelScope.launch {
        preferencesDataSource.clearPref()
        sendEvent(ScreenEvent.Login)
    }

    sealed class ScreenActions {
        object OnRetry : ScreenActions()
        object OnLogout : ScreenActions()
        data class OnClickItem(val path: MainViewModel.Navigation) : ScreenActions()
    }

    sealed class ScreenEvent {
        object Login : ScreenEvent()
        object ExpiredScreen : ScreenEvent()
        data class Navigate(val path: MainViewModel.Navigation) : ScreenEvent()
    }
}

