package com.bulletapps.candypricer.data

import androidx.room.*
import com.bulletapps.candypricer.domain.model.Supply

@Dao
interface SupplyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSupply(supply: Supply): Supply

    @Query("SELECT * FROM supplies WHERE id=:id")
    fun getSupply(id: String): Supply?

    @Query("SELECT * FROM supplies")
    fun getAllSupplies() : List<Supply>

    @Update
    suspend fun updateSupply(supply: Supply) : Supply

    @Delete
    suspend fun deleteSupply(supply: Supply)
}