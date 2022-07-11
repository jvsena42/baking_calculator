package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.datasource.CandyPricerDataSource
import com.bulletapps.candypricer.data.datasource.CandyPricerRemoteDataSource
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.repository.CandyPricerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SupplyCandyPricerDataSource {

    @Singleton
    @Provides
    fun providesCandyPricerRemoteDataSource (api: CandyPricerApi): CandyPricerDataSource {
        return CandyPricerRemoteDataSource(api)
    }
}