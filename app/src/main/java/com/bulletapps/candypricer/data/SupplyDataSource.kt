package com.bulletapps.candypricer.data

import com.bulletapps.candypricer.domain.model.Supply

interface SupplyDataSource {
    fun createSupply(supply: Supply): Supply

    fun getSupply(id: String): Supply

    fun getAllSupplies() : List<Supply>

    fun updateSupply(id: String)

    fun deleteSupply(id: String)
}