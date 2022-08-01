package com.bulletapps.candypricer.presentation.ui.scenes.main.menu

import androidx.lifecycle.ViewModel
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import com.bulletapps.candypricer.domain.usecase.user.IsExpiredUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val isExpiredUserUseCase: IsExpiredUserUseCase
) : ViewModel() {

    suspend fun setup() {
        val result = getUserUseCase()

        if (result is Resource.Success) {
            val isExpired = isExpiredUserUseCase(result.data?.expirationDate!!)

        }
    }

}

