package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.data.datasource.remote.CandyPricerDataSource
import com.bulletapps.candypricer.data.mapper.SupplyMapper.toSupplyModelList
import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitModelList
import com.bulletapps.candypricer.data.mapper.toProductModelList
import com.bulletapps.candypricer.data.mapper.toUserModel
import com.bulletapps.candypricer.data.mapper.toUserModelList
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.presentation.util.safeRequest
import com.bulletapps.candypricer.presentation.util.safeRequest2
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

    override suspend fun getUser() = safeRequest2(dispatcher) {
        dataSource.getUser().toUserModel()
    }

    override suspend fun getUsers() = safeRequest2(dispatcher) {
        dataSource.getUsers().toUserModelList()
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

    override suspend fun getProducts() = safeRequest2(dispatcher) {
        dataSource.getProducts().toProductModelList()
    }

    override suspend fun updateProduct(parameters: UpdateProductParameters) = safeRequest(dispatcher) {
        dataSource.updateProduct(parameters)
    }

    override suspend fun deleteProduct(id: Int) = safeRequest(dispatcher) {
        dataSource.deleteProduct(id)
    }

    override suspend fun createSupply(parameters: CreateSupplyParameters) = safeRequest(dispatcher) {
        dataSource.createSupply(parameters)
    }

    override suspend fun getSupplies() = safeRequest2(dispatcher) {
        dataSource.getSupplies().toSupplyModelList()
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

    override suspend fun getUnits() = safeRequest2(dispatcher) {
        dataSource.getUnits().toUnitModelList()
    }
}