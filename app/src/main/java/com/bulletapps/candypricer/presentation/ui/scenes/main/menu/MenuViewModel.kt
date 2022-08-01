package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.domain.usecase.user.IsExpiredUserUseCase
import com.bulletapps.candypricer.presentation.util.EventFlow
import com.bulletapps.candypricer.presentation.util.EventFlowImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val isExpiredUserUseCase: IsExpiredUserUseCase
) : ViewModel(), EventFlow<MenuViewModel.ScreenEvent> by EventFlowImpl()  {

    suspend fun setup() {
        val result = getUserUseCase()

        if (result is Resource.Success) {
            val isExpired = isExpiredUserUseCase(result.data?.expirationDate!!)

            if(isExpired) {
                viewModelScope.sendEvent(ScreenEvent.ExpiredScreen)
            }
        }
    }

    sealed class ScreenEvent {
        object ExpiredScreen : ScreenEvent()
    }
}

