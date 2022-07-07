package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.data.datasource.CandyPricerDataSource
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import com.bulletapps.candypricer.domain.model.Supply
import javax.inject.Inject

class CandyPricerRepositoryImpl @Inject constructor(
    private val dataSource: CandyPricerDataSource
): CandyPricerRepository {
    override suspend fun createUser(parameters: CreateUserParameters): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(parameters: LoginParameters): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun createProduct(parameters: CreateProductParameters): ProductResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(): List<ProductResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createSupply(parameters: CreateSupplyParameters): SupplyResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getSupplies(): List<SupplyResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createUnit(parameters: CreateUnitParameters): UnitResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUnits(
        key: String,
        value: String,
        disabled: Boolean
    ): List<UnitResponse> {
        TODO("Not yet implemented")
    }

}