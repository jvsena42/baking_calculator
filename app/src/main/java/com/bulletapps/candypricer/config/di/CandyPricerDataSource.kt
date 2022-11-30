package com.bulletapps.candypricer.config.di

import android.content.Context
import com.bulletapps.candypricer.config.db.UnitsDAO
import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.datasource.remote.CandyPricerDataSource
import com.bulletapps.candypricer.data.datasource.remote.CandyPricerRemoteDataSource
import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class SupplyCandyPricerDataSource {

    @Singleton
    @Provides
    fun providesCandyPricerRemoteDataSource(api: CandyPricerApi): CandyPricerDataSource {
        return CandyPricerRemoteDataSource(api)
    }

    @Singleton
    @Provides
    fun providesCandyPricerPreferencesDataSource(@ApplicationContext appContext: Context): PreferencesDataSource {
        return PreferencesDataSource(appContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE))
    }
}