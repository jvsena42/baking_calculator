package com.bulletapps.candypricer.data.db.di

import android.app.Application
import androidx.room.Room
import com.bulletapps.candypricer.data.db.SupplyDAO
import com.bulletapps.candypricer.data.db.SupplyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SupplyDatabaseModule {

    @Provides
    @Singleton
    fun provideSupplyDatabase(app: Application): SupplyDatabase{
        return Room.databaseBuilder(app, SupplyDatabase::class.java,"supply_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSupplyDao(supplyDatabase: SupplyDatabase): SupplyDAO{
        return supplyDatabase.getSupplyDao()
    }
}