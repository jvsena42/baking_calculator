package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.data.datasource.SupplyDataSource
import com.bulletapps.candypricer.data.datasource.SupplyLocalDataSourceImpl
import com.bulletapps.candypricer.data.db.SupplyDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SupplyLocalDataSourceModule {

    @Singleton
    @Provides
    fun providesSupplyLocalDataSourceModule(supplyDAO: SupplyDAO): SupplyDataSource {
        return SupplyLocalDataSourceImpl(supplyDAO)
    }
}