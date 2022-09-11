package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*

interface CandyPricerRepository {

    suspend fun createUser(parameters: CreateUserParameters): Resource<LoginResponse>

    suspend fun getUser(): Resource<UserResponse>

    suspend fun getUsers(): Resource<List<UserResponse>>

    suspend fun updateUser(parameters: UpdateUserParameters): Resource<UserResponse>

    suspend fun deleteUser(): Resource<UserResponse>

    suspend fun login(parameters: LoginParameters): Resource<LoginResponse>

    suspend fun createProduct(parameters: CreateProductParameters): Resource<ProductResponse>

    suspend fun getProducts(): Resource<List<ProductResponse>>

    suspend fun deleteProduct(id: Int): Resource<ProductResponse>

    suspend fun createSupply(parameters: CreateSupplyParameters): Resource<SupplyResponse>

    suspend fun getSupplies(): Resource<List<SupplyResponse>>

    suspend fun deleteSupply(id: Int): Resource<CreateSupplyParameters>

    suspend fun updateSupply(parameters: UpdateSupplyParameters): Resource<SupplyResponse>

    suspend fun createUnit(parameters: CreateUnitParameters): Resource<UnitResponse>

    suspend fun getUnits(): Resource<List<UnitResponse>>
}