package com.bulletapps.candypricer.data.db

import androidx.room.*
import com.bulletapps.candypricer.data.model.Supply

@Dao
interface SupplyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSupply(supply: Supply)

    @Query("SELECT * FROM supplies WHERE id=:id")
    fun getSupply(id: Int): Supply?

    @Query("SELECT * FROM supplies")
    fun getAllSupplies() : List<Supply>

    @Update
    suspend fun updateSupply(supply: Supply)

    @Delete
    suspend fun deleteSupply(supply: Supply)
}