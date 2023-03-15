package com.bulletapps.candypricer.config.db

import androidx.room.*
import com.bulletapps.candypricer.data.entities.UnitEntity

@Dao
interface UnitsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUnits(units: List<UnitEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnits(units: List<UnitEntity>)

    @Query("SELECT * FROM units")
    suspend fun getUnits(): List<UnitEntity>?

    @Query("DELETE FROM units")
    suspend fun deleteUnits()
}