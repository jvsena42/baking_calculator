package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.BuildConfig
import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.datasource.local.PreferencesDataSource
import com.bulletapps.candypricer.data.service.OkHttpInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(prefs: PreferencesDataSource): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(OkHttpInterceptor(prefs))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideCandyPricerApi(retrofit: Retrofit): CandyPricerApi {
        return retrofit.create(CandyPricerApi::class.java)
    }
}