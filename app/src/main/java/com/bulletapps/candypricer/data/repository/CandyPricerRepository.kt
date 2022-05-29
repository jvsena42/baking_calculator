package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.data.model.Supply

interface CandyPricerRepository {
    suspend fun createSupply(supply: Supply)

    suspend fun getSupply(id: String): Supply?

    suspend fun getAllSupplies() : List<Supply>

    suspend fun updateSupply(supply: Supply)

    suspend fun deleteSupply(supply: Supply)
}