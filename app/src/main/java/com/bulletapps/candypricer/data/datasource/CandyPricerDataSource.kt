package com.bulletapps.candypricer.data.datasource

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import retrofit2.Response
import retrofit2.http.*

interface CandyPricerDataSource {

    suspend fun createUser(parameters: CreateUserParameters): LoginResponse

    suspend fun getUser(): UserResponse

    suspend fun updateUser(parameters: UpdateUserParameters)

    suspend fun updateExpirationDate(id: Int, parameters: UpdateExpirationDateParameters)

    suspend fun deleteUser()

    suspend fun getUsers(): List<UserResponse>

    suspend fun login(parameters: LoginParameters): LoginResponse

    suspend fun createProduct(parameters: CreateProductParameters)

    suspend fun getProducts(): List<ProductResponse>

    suspend fun deleteProduct(id: Int)

    suspend fun createSupply(parameters: CreateSupplyParameters)

    suspend fun getSupplies(): List<SupplyResponse>

    suspend fun updateSupply(parameters: UpdateSupplyParameters)

    suspend fun deleteSupply(id: Int)

    suspend fun createUnit(parameters: CreateUnitParameters)

    suspend fun getUnits(): List<UnitResponse>
}