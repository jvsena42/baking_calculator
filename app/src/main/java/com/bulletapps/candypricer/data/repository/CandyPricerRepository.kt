package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.LoginResponse
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.domain.model.UnitModel
import com.bulletapps.candypricer.domain.model.UserModel

interface CandyPricerRepository {

    suspend fun createUser(parameters: CreateUserParameters): Resource<LoginResponse>

    suspend fun getUser(): Result<UserModel>

    suspend fun getUsers(): Result<List<UserModel>>

    suspend fun updateUser(parameters: UpdateUserParameters): Resource<Unit>

    suspend fun updateExpirationDate(id:Int, parameters: UpdateExpirationDateParameters): Resource<Unit>

    suspend fun deleteUser(): Resource<Unit>

    suspend fun login(parameters: LoginParameters): Resource<LoginResponse>

    suspend fun createProduct(parameters: CreateProductParameters): Resource<Unit>

    suspend fun getProducts(): Result<List<ProductModel>>

    suspend fun updateProduct(parameters: UpdateProductParameters): Resource<Unit>

    suspend fun deleteProduct(id: Int): Resource<Unit>

    suspend fun createSupply(parameters: CreateSupplyParameters): Resource<Unit>

    suspend fun getSupplies(): Result<List<SupplyModel>>

    suspend fun deleteSupply(id: Int): Resource<Unit>

    suspend fun updateSupply(parameters: UpdateSupplyParameters): Resource<Unit>

    suspend fun createUnit(parameters: CreateUnitParameters): Resource<Unit>

    suspend fun getUnits(isRefresh: Boolean = false): Result<List<UnitModel>>

    suspend fun deleteUnits(): Result<Unit>
}