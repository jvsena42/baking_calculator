package com.bulletapps.candypricer.config.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}