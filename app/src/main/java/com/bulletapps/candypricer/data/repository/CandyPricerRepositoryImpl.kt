package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.data.datasource.local.LocalDataSource
import com.bulletapps.candypricer.data.datasource.remote.CandyPricerDataSource
import com.bulletapps.candypricer.data.mapper.SupplyMapper.toSupplyModelList
import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitEntity
import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitModel
import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitModelList
import com.bulletapps.candypricer.data.mapper.toProductModelList
import com.bulletapps.candypricer.data.mapper.toUserModel
import com.bulletapps.candypricer.data.mapper.toUserModelList
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.domain.model.UnitModel
import com.bulletapps.candypricer.presentation.util.safeRequest
import com.bulletapps.candypricer.presentation.util.safeRequest2
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CandyPricerRepositoryImpl @Inject constructor(
    private val remoteDataSource: CandyPricerDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CandyPricerRepository {
    override suspend fun createUser(parameters: CreateUserParameters) = safeRequest(dispatcher) {
        remoteDataSource.createUser(parameters)
    }

    override suspend fun getUser() = safeRequest2(dispatcher) {
        remoteDataSource.getUser().toUserModel()
    }

    override suspend fun getUsers() = safeRequest2(dispatcher) {
        remoteDataSource.getUsers().toUserModelList()
    }

    override suspend fun updateUser(parameters: UpdateUserParameters) = safeRequest(dispatcher) {
        remoteDataSource.updateUser(parameters)
    }

    override suspend fun updateExpirationDate(id: Int, parameters: UpdateExpirationDateParameters) =
        safeRequest(dispatcher) {
            remoteDataSource.updateExpirationDate(id, parameters)
        }

    override suspend fun deleteUser() = safeRequest(dispatcher) {
        remoteDataSource.deleteUser()
    }

    override suspend fun login(parameters: LoginParameters) = safeRequest(dispatcher) {
        remoteDataSource.login(parameters)
    }

    override suspend fun createProduct(parameters: CreateProductParameters) =
        safeRequest(dispatcher) {
            remoteDataSource.createProduct(parameters)
        }

    override suspend fun getProducts() = safeRequest2(dispatcher) {
        remoteDataSource.getProducts().toProductModelList()
    }

    override suspend fun updateProduct(parameters: UpdateProductParameters) =
        safeRequest(dispatcher) {
            remoteDataSource.updateProduct(parameters)
        }

    override suspend fun deleteProduct(id: Int) = safeRequest(dispatcher) {
        remoteDataSource.deleteProduct(id)
    }

    override suspend fun createSupply(parameters: CreateSupplyParameters) =
        safeRequest(dispatcher) {
            remoteDataSource.createSupply(parameters)
        }

    override suspend fun getSupplies() = safeRequest2(dispatcher) {
        remoteDataSource.getSupplies().toSupplyModelList()
    }

    override suspend fun deleteSupply(id: Int) = safeRequest(dispatcher) {
        remoteDataSource.deleteSupply(id)
    }

    override suspend fun updateSupply(parameters: UpdateSupplyParameters) =
        safeRequest(dispatcher) {
            remoteDataSource.updateSupply(parameters)
        }

    override suspend fun createUnit(parameters: CreateUnitParameters) = safeRequest(dispatcher) {
        remoteDataSource.createUnit(parameters)
    }

    override suspend fun getUnits(isRefresh: Boolean) =
        safeRequest2(dispatcher) {
            return@safeRequest2 if (isRefresh) {
                remoteDataSource.getUnits().toUnitModelList().also { remoteUnits ->
                    localDataSource.getUnits().let { units ->
                        if (units.isNullOrEmpty()) {
                            localDataSource.createUnits(remoteUnits.toUnitEntity())
                        } else {
                            localDataSource.updateUnits(remoteUnits.toUnitEntity())
                        }
                    }
                }
            } else {
                localDataSource.getUnits().toUnitModel()
            }
        }
}