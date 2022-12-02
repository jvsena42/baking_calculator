package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: CandyPricerRepository,
    private val preferencesDataSource: PreferencesDataSource
    ) {

    suspend operator fun invoke() {
        repository.deleteUnits()
        preferencesDataSource.clearPref()
    }
}