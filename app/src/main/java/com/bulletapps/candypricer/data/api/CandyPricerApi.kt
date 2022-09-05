package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import retrofit2.http.*

interface CandyPricerApi {
    @POST("/user")
    suspend fun createUser(
        @Body parameters: CreateUserParameters
    ): LoginResponse

    @GET("/user")
    suspend fun getUser(): UserResponse

    @GET("/user")
    suspend fun getUsers(): List<UserResponse>

    @POST("/login")
    suspend fun login(
        @Body parameters: LoginParameters
    ): LoginResponse

    @POST("/product")
    suspend fun createProduct(
        @Body parameters: CreateProductParameters
    ): ProductResponse

    @GET("/product")
    suspend fun getProducts(): List<ProductResponse>

    @DELETE("/product/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): ProductResponse

    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    ): SupplyResponse

    @GET("/supply")
    suspend fun getSupplies(): List<SupplyResponse>

    @PUT("/supply")
    suspend fun updateSupply(
        @Body parameters: UpdateSupplyParameters
    ): SupplyResponse

    @DELETE("/supply/{id}")
    suspend fun deleteSupply(
        @Path("id") id: Int
    ): CreateSupplyParameters

    @POST("/unit")
    suspend fun createUnit(
        @Body parameters: CreateUnitParameters
    ): UnitResponse

    @GET("/unit")
    suspend fun getUnits(): List<UnitResponse>
}
