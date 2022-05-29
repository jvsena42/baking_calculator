package com.bulletapps.candypricer.data.datasource

import com.bulletapps.candypricer.data.db.SupplyDAO
import com.bulletapps.candypricer.data.model.Supply
import javax.inject.Inject

class SupplyLocalDataSourceImpl @Inject constructor(private val supplyDAO: SupplyDAO ): SupplyDataSource {
    override suspend fun createSupply(supply: Supply) = supplyDAO.createSupply(supply)

    override suspend fun getSupply(id: String) = supplyDAO.getSupply(id)

    override suspend fun getAllSupplies() = supplyDAO.getAllSupplies()

    override suspend fun updateSupply(supply: Supply) = supplyDAO.updateSupply(supply)

    override suspend fun deleteSupply(supply: Supply) = supplyDAO.deleteSupply(supply)
}