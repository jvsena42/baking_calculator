package com.bulletapps.candypricer.data.datasource.remote

import com.bulletapps.candypricer.data.api.CandyPricerApi
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import javax.inject.Inject

class CandyPricerRemoteDataSource @Inject constructor(private val api: CandyPricerApi) : CandyPricerDataSource {

    override suspend fun createUser(parameters: CreateUserParameters) = api.createUser(parameters)

    override suspend fun getUser() = api.getUser()

    override suspend fun updateExpirationDate(id: Int, expirationDate: String) {
        api.updateExpirationDate(id, expirationDate)
    }

    override suspend fun deleteUser() {
        if (!api.deleteUser().isSuccessful) throw Exception()
    }

    override suspend fun getUsers() = api.getUsers()

    override suspend fun login(parameters: LoginParameters): LoginResponse = api.login(parameters)

    override suspend fun createProduct(parameters: CreateProductParameters) {
        if (!api.createProduct(parameters).isSuccessful) throw Exception()
    }

    override suspend fun getProducts(): List<ProductResponse> = api.getProducts()

    override suspend fun updateProduct(parameters: UpdateProductParameters) {
        if (!api.updateProduct(parameters).isSuccessful) throw Exception()
    }

    override suspend fun deleteProduct(id: Int) {
        if(!api.deleteProduct(id).isSuccessful) throw Exception()
    }

    override suspend fun createSupply(parameters: CreateSupplyParameters) {
        if (!api.createSupply(parameters).isSuccessful) throw Exception()
    }

    override suspend fun getSupplies(): List<SupplyResponse> = api.getSupplies()

    override suspend fun updateSupply(parameters: UpdateSupplyParameters) {
        if (!api.updateSupply(parameters).isSuccessful) throw Exception()
    }

    override suspend fun deleteSupply(id: Int) {
        if(!api.deleteSupply(id).isSuccessful) throw Exception()
    }

    override suspend fun createUnit(parameters: CreateUnitParameters) {
        if (!api.createUnit(parameters).isSuccessful) throw Exception()
    }

    override suspend fun getUnits(): List<UnitResponse> = api.getUnits()
}