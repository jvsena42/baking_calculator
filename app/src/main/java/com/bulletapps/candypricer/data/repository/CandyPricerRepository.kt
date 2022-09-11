package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*

interface CandyPricerRepository {

    suspend fun createUser(parameters: CreateUserParameters): Resource<LoginResponse>

    suspend fun getUser(): Resource<UserResponse>

    suspend fun getUsers(): Resource<List<UserResponse>>

    suspend fun updateUser(parameters: UpdateUserParameters): Resource<Unit>

    suspend fun deleteUser(): Resource<Unit>

    suspend fun login(parameters: LoginParameters): Resource<LoginResponse>

    suspend fun createProduct(parameters: CreateProductParameters): Resource<Unit>

    suspend fun getProducts(): Resource<List<ProductResponse>>

    suspend fun deleteProduct(id: Int): Resource<Unit>

    suspend fun createSupply(parameters: CreateSupplyParameters): Resource<Unit>

    suspend fun getSupplies(): Resource<List<SupplyResponse>>

    suspend fun deleteSupply(id: Int): Resource<Unit>

    suspend fun updateSupply(parameters: UpdateSupplyParameters): Resource<Unit>

    suspend fun createUnit(parameters: CreateUnitParameters): Resource<Unit>

    suspend fun getUnits(): Resource<List<UnitResponse>>
}