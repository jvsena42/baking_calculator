package com.bulletapps.candypricer.data.datasource

import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import javax.inject.Inject

class CandyPricerRemoteDataSource @Inject constructor(private val api: CandyPricerApi) : CandyPricerDataSource {

    override suspend fun createUser(parameters: CreateUserParameters) = api.createUser(parameters)

    override suspend fun getUser() = api.getUser()

    override suspend fun updateUser(parameters: UpdateUserParameters) = api.updateUser(parameters)

    override suspend fun updateExpirationDate(id: Int, parameters: UpdateExpirationDateParameters) = api.updateExpirationDate(id, parameters)

    override suspend fun deleteUser() = api.deleteUser()

    override suspend fun getUsers() = api.getUsers()

    override suspend fun login(parameters: LoginParameters): LoginResponse = api.login(parameters)

    override suspend fun createProduct(parameters: CreateProductParameters) = api.createProduct(parameters)

    override suspend fun getProducts(): List<ProductResponse> = api.getProducts()

    override suspend fun deleteProduct(id: Int) = api.deleteProduct(id)

    override suspend fun createSupply(parameters: CreateSupplyParameters) = api.createSupply(parameters)

    override suspend fun getSupplies(): List<SupplyResponse> = api.getSupplies()

    override suspend fun updateSupply(parameters: UpdateSupplyParameters) = api.updateSupply(parameters)

    override suspend fun deleteSupply(id: Int) = api.deleteSupply(id)

    override suspend fun createUnit(parameters: CreateUnitParameters) = api.createUnit(parameters)

    override suspend fun getUnits(): List<UnitResponse> = api.getUnits()
}