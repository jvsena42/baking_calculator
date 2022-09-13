package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.datasource.CandyPricerDataSource
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import com.bulletapps.candypricer.presentation.util.safeRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CandyPricerRepositoryImpl @Inject constructor(
    private val dataSource: CandyPricerDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): CandyPricerRepository {
    override suspend fun createUser(parameters: CreateUserParameters) = safeRequest(dispatcher) {
        dataSource.createUser(parameters)
    }

    override suspend fun getUser() = safeRequest(dispatcher) {
        dataSource.getUser()
    }

    override suspend fun getUsers() = safeRequest(dispatcher) {
        dataSource.getUsers()
    }

    override suspend fun updateUser(parameters: UpdateUserParameters) = safeRequest(dispatcher) {
        dataSource.updateUser(parameters)
    }

    override suspend fun updateExpirationDate(id: Int, parameters: UpdateExpirationDateParameters) = safeRequest(dispatcher) {
        dataSource.updateExpirationDate(id, parameters)
    }

    override suspend fun deleteUser() = safeRequest(dispatcher) {
        dataSource.deleteUser()
    }

    override suspend fun login(parameters: LoginParameters) = safeRequest(dispatcher) {
        dataSource.login(parameters)
    }

    override suspend fun createProduct(parameters: CreateProductParameters) = safeRequest(dispatcher) {
        dataSource.createProduct(parameters)
    }

    override suspend fun getProducts(): Resource<List<ProductResponse>> = safeRequest(dispatcher) {
        dataSource.getProducts()
    }

    override suspend fun deleteProduct(id: Int) = safeRequest(dispatcher) {
        dataSource.deleteProduct(id)
    }

    override suspend fun createSupply(parameters: CreateSupplyParameters) = safeRequest(dispatcher) {
        dataSource.createSupply(parameters)
    }

    override suspend fun getSupplies(): Resource<List<SupplyResponse>> = safeRequest(dispatcher) {
        dataSource.getSupplies()
    }

    override suspend fun deleteSupply(id: Int) = safeRequest(dispatcher) {
        dataSource.deleteSupply(id)
    }

    override suspend fun updateSupply(parameters: UpdateSupplyParameters) = safeRequest(dispatcher) {
        dataSource.updateSupply(parameters)
    }

    override suspend fun createUnit(parameters: CreateUnitParameters) = safeRequest(dispatcher) {
        dataSource.createUnit(parameters)
    }

    override suspend fun getUnits() = safeRequest(dispatcher) {
        dataSource.getUnits()
    }
}