package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.BuildConfig
import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.api.ProductApi
import com.bulletapps.candypricer.data.api.SupplyApi
import com.bulletapps.candypricer.data.api.UnitApi
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(OkHttpInterceptor())
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

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSupplyApi(retrofit: Retrofit): SupplyApi {
        return retrofit.create(SupplyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUnitApi(retrofit: Retrofit): UnitApi {
        return retrofit.create(UnitApi::class.java)
    }
}