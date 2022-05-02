package com.bulletapps.candypricer.data

import com.bulletapps.candypricer.domain.model.Supply

interface SupplyDataSource {
    suspend fun createSupply(supply: Supply): Supply

    suspend fun getSupply(id: String): Supply?

    suspend fun getAllSupplies() : List<Supply>

    suspend fun updateSupply(supply: Supply) : Supply

    suspend fun deleteSupply(id: String)
}