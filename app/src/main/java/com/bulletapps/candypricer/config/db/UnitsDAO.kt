package com.bulletapps.candypricer.config.db

import androidx.room.*
import com.bulletapps.candypricer.data.entities.UnitEntity

@Dao
interface UnitsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUnits(units: List<UnitEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUnits(units: List<UnitEntity>)

    @Query("SELECT * FROM units")
    fun getUnits(): List<UnitEntity>?

    @Query("DELETE FROM units")
    fun deleteUnits()
}