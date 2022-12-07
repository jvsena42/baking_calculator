package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.presentation.util.EMPTY_STRING
import com.bulletapps.candypricer.presentation.util.PreferencesKeys
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
    ) {
    suspend operator fun invoke(): Boolean {
        val userToken = preferencesDataSource.getPref(PreferencesKeys.ACCESS_TOKEN, EMPTY_STRING)
        return userToken.isNotEmpty()
    }
}