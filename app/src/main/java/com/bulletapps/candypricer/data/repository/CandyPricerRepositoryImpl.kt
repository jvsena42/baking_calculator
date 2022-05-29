package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.data.datasource.SupplyDataSource
import com.bulletapps.candypricer.data.model.Supply
import javax.inject.Inject

class CandyPricerRepositoryImpl @Inject constructor(
    private val supplyDataSource: SupplyDataSource
): CandyPricerRepository {
    override suspend fun createSupply(supply: Supply) = supplyDataSource.createSupply(supply)

    override suspend fun getSupply(id: Int) = supplyDataSource.getSupply(id)

    override suspend fun getAllSupplies(): List<Supply> = supplyDataSource.getAllSupplies()

    override suspend fun updateSupply(supply: Supply) = supplyDataSource.updateSupply(supply)

    override suspend fun deleteSupply(supply: Supply) = supplyDataSource.deleteSupply(supply)
}