package com.bulletapps.candypricer.data.datasource

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import retrofit2.http.*

interface CandyPricerDataSource {

    suspend fun createUser(parameters: CreateUserParameters): LoginResponse

    suspend fun getUser(): UserResponse

    suspend fun login(parameters: LoginParameters): LoginResponse

    suspend fun createProduct(parameters: CreateProductParameters): ProductResponse

    suspend fun getProducts(): List<ProductResponse>

    suspend fun createSupply(parameters: CreateSupplyParameters): SupplyResponse

    suspend fun getSupplies(): List<SupplyResponse>

    suspend fun deleteSupply(id: Int): CreateSupplyParameters

    suspend fun createUnit(parameters: CreateUnitParameters): UnitResponse

    suspend fun getUnits(): List<UnitResponse>
}