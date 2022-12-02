package com.bulletapps.candypricer.config.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bulletapps.candypricer.data.entities.UnitEntity

@Database(
    entities = [UnitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CandyPricerDatabase : RoomDatabase() {

    abstract fun getUnitsDAO(): UnitsDAO
}