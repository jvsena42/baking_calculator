package com.bulletapps.candypricer.data.supply

import androidx.room.Database
import com.bulletapps.candypricer.domain.model.Supply

@Database(
    entities = [Supply::class],
    version = 1,
    exportSchema = false
)
abstract class SupplyDatabase {

    abstract fun getSupplyDao(): SupplyDAO
}