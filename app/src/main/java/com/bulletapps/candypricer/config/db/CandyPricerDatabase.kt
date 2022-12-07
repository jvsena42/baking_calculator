package com.bulletapps.candypricer.config.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bulletapps.candypricer.data.entities.UnitEntity
import com.bulletapps.candypricer.data.entities.UserEntity

@Database(
    entities = [UnitEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CandyPricerDatabase : RoomDatabase() {

    abstract fun getUnitsDAO(): UnitsDAO

    abstract fun getUserDAO(): UserDAO
}