package com.bulletapps.candypricer.data.datasource

import com.bulletapps.candypricer.data.model.Supply

class SupplyLocalDataSourceImpl constructor( ): SupplyDataSource {
    override suspend fun createSupply(supply: Supply): Supply {
        TODO("Not yet implemented")
    }

    override suspend fun getSupply(id: String): Supply? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSupplies(): List<Supply> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSupply(supply: Supply): Supply {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSupply(id: String) {
        TODO("Not yet implemented")
    }
}