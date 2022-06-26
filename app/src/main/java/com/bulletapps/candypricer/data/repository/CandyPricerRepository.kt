package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.domain.model.Supply

interface CandyPricerRepository {
    suspend fun createSupply(supply: Supply)

    suspend fun getSupply(id: Int) : Supply?

    suspend fun getAllSupplies() : List<Supply>

    suspend fun updateSupply(supply: Supply)

    suspend fun deleteSupply(supply: Supply)
}