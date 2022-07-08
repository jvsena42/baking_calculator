package com.bulletapps.candypricer.data.repository

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import com.bulletapps.candypricer.domain.model.Result

interface CandyPricerRepository {

    suspend fun createUser(parameters: CreateUserParameters): Result<UserResponse>

    suspend fun login(parameters: LoginParameters): Result<LoginResponse>

    suspend fun createProduct(parameters: CreateProductParameters): Result<ProductResponse>

    suspend fun getProducts(): Result<List<ProductResponse>>

    suspend fun createSupply(parameters: CreateSupplyParameters): Result<SupplyResponse>

    suspend fun getSupplies(): Result<List<SupplyResponse>>

    suspend fun createUnit(parameters: CreateUnitParameters): Result<UnitResponse>

    suspend fun getUnits(
        key: String,
        value: String,
        disabled: Boolean,
    ): Result<List<UnitResponse>>
}