package com.bulletapps.candypricer.config.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.datasource.CandyPricerDataSource
import com.bulletapps.candypricer.data.datasource.CandyPricerRemoteDataSource
import com.bulletapps.candypricer.data.datasource.PreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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