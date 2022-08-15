package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.domain.model.MenuModel
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.domain.usecase.user.IsExpiredUserUseCase
import com.bulletapps.candypricer.presentation.ui.scenes.main.user.login.LoginViewModel
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val isExpiredUserUseCase: IsExpiredUserUseCase
) : ViewModel(), EventFlow<MenuViewModel.ScreenEvent> by EventFlowImpl()  {

    val uiState = UIState()

    suspend fun setup(menuItems: List<MenuModel>) {
        uiState.menuItems.value = menuItems
        val result = getUserUseCase()

        if (result is Resource.Success) {
            val isExpired = isExpiredUserUseCase(result.data?.expirationDate!!)

            if(isExpired) {
                viewModelScope.sendEvent(ScreenEvent.ExpiredScreen)
            }
        }
    }

    fun onAction(action: ScreenActions) = when(action) {
        is ScreenActions.OnClickItem -> viewModelScope.sendEvent(ScreenEvent.Navigate(action.path))
    }

    sealed class ScreenActions {
        data class OnClickItem(val path: String) : ScreenActions()
    }

    sealed class ScreenEvent {
        object ExpiredScreen : ScreenEvent()
        data class Navigate(val path: String) : ScreenEvent()
    }

    class UIState {
        val menuItems = MutableStateFlow<List<MenuModel>>(emptyList())
    }
}

