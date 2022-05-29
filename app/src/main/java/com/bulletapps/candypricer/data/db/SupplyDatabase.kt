package com.bulletapps.candypricer.data.db

import androidx.room.Database
import com.bulletapps.candypricer.data.model.Supply

@Database(
    entities = [Supply::class],
    version = 1,
    exportSchema = false
)
abstract class SupplyDatabase {

    abstract fun getSupplyDao(): SupplyDAO
}