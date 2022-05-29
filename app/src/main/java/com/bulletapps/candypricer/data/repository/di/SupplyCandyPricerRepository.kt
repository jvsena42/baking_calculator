package com.bulletapps.candypricer.data.repository.di

import com.bulletapps.candypricer.data.datasource.SupplyDataSource
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.repository.CandyPricerRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn()
class SupplyCandyPricerRepository {
    fun providesCandyPricerRepository (supplyLocalDataSource: SupplyDataSource): CandyPricerRepository {
        return CandyPricerRepositoryImpl(supplyLocalDataSource)
    }
}