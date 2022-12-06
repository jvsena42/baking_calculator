package com.bulletapps.candypricer.config.di

import android.app.Application
import androidx.room.Room
import com.bulletapps.candypricer.config.db.CandyPricerDatabase
import com.bulletapps.candypricer.config.db.UnitsDAO
import com.bulletapps.candypricer.config.db.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesCandyPricerDatabase(app: Application): CandyPricerDatabase {
        return Room.databaseBuilder(app, CandyPricerDatabase::class.java, "candy_pricer_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesUnitsDao(database: CandyPricerDatabase): UnitsDAO {
        return database.getUnitsDAO()
    }

    @Singleton
    @Provides
    fun providesUserDAO(database: CandyPricerDatabase): UserDAO {
        return database.getUserDAO()
    }
}