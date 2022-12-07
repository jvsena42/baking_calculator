package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.data.parameters.LoginParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.presentation.util.PreferencesKeys
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: CandyPricerRepository,
    private val getUnitsUseCase: GetUnitsUseCase,
    private val userUseCase: GetUserUseCase,
    private val preferencesDataSource: PreferencesDataSource
    ) {
    suspend operator fun invoke(parameters: LoginParameters) = repository.login(parameters).also {
        if (it is Resource.Success) {
            preferencesDataSource.setPref(PreferencesKeys.ACCESS_TOKEN, it.data?.accessToken.orEmpty())
            getUnitsUseCase.invoke(isRefresh = true)
            userUseCase.invoke(isRefresh = true)
        }
    }
}