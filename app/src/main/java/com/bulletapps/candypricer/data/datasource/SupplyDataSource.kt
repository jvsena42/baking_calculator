package com.bulletapps.candypricer.data.datasource

import com.bulletapps.candypricer.data.model.Supply

interface SupplyDataSource {
    suspend fun createSupply(supply: Supply)

    suspend fun getSupply(id: String): Supply?

    suspend fun getAllSupplies() : List<Supply>

    suspend fun updateSupply(supply: Supply)

    suspend fun deleteSupply(supply: Supply)
}