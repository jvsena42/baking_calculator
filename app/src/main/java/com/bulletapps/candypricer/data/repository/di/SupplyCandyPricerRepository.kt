package com.bulletapps.candypricer.data.repository.di

import com.bulletapps.candypricer.data.datasource.SupplyDataSource
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.repository.CandyPricerRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SupplyCandyPricerRepository {
    fun providesCandyPricerRepository (supplyLocalDataSource: SupplyDataSource): CandyPricerRepository {
        return CandyPricerRepositoryImpl(supplyLocalDataSource)
    }
}