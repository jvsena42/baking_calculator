package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.data.datasource.local.LocalDataSource
import com.bulletapps.candypricer.data.datasource.remote.CandyPricerDataSource
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.repository.CandyPricerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CandyPricerRepository {

    @Singleton
    @Provides
    fun providesCandyPricerRepository(
        remoteDataSource: CandyPricerDataSource,
        localDataSource: LocalDataSource
    ): CandyPricerRepository {
        return CandyPricerRepositoryImpl(remoteDataSource, localDataSource)
    }
}