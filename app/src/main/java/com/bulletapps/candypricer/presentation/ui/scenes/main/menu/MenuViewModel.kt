package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.domain.usecase.user.IsExpiredUserUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.MainViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.LoginViewModel
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.products.ProductsViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import com.bulletapps.candypricer.presentation.util.orFalse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val isExpiredUserUseCase: IsExpiredUserUseCase,
    private val preferencesDataSource: PreferencesDataSource,
    ) : ViewModel(), EventFlow<MenuViewModel.ScreenEvent> by EventFlowImpl()  {

    val uiState = MenuUIState()

    private val menuClient = mutableListOf(
        MenuModel(R.string.my_products, R.drawable.ic_store, MainViewModel.Navigation.Products),
        MenuModel(R.string.supplies, R.drawable.ic_shopping_cart, MainViewModel.Navigation.Supplies),
        MenuModel(R.string.settings, R.drawable.ic_build, MainViewModel.Navigation.Settings),
    )

    private val menuAdmin = mutableListOf<MenuModel>().apply {
        addAll(menuClient)
        add(MenuModel(R.string.clients, R.drawable.ic_clients, MainViewModel.Navigation.Clients))
    }

    suspend fun setup() {
        val result = getUserUseCase()

        if (result is Resource.Success) {
            handleExpired(result)
            handleUserType(result)
        } else {
//            TODO Remove comment when fix get users
//            uiState.onFailure(ScreenActions.OnRetry, ScreenActions.OnLogout)
            uiState.onSuccess(menuClient)
        }
    }

    private fun handleUserType(result: Resource<UserResponse>) {
        val isAdmin = result.data?.isAdmin.orFalse()
        val items = if (isAdmin) menuAdmin else menuClient
        uiState.onSuccess(items)
    }

    private suspend fun handleExpired(
        result: Resource<UserResponse>
    ) {
        val isExpired = isExpiredUserUseCase(result.data?.expirationDate!!)
        if (isExpired) {
            viewModelScope.sendEvent(ScreenEvent.ExpiredScreen)
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickItem -> viewModelScope.sendEvent(ScreenEvent.Navigate(action.path))
        ScreenActions.OnLogout -> onClickLogout()
        ScreenActions.OnRetry -> viewModelScope.launch { setup() }
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

