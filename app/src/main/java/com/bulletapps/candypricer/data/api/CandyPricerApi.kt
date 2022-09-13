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

    @PUT("/user")
    suspend fun updateUser(
        @Body parameters: UpdateUserParameters
    )

    @PUT("/admin/{id}")
    suspend fun updateExpirationDate(
        @Path("id") id: Int,
        @Body parameters: UpdateExpirationDateParameters
    )

    @DELETE("/user")
    suspend fun deleteUser()

    @GET("/admin/users")
    suspend fun getUsers(): List<UserResponse>

    @POST("/login")
    suspend fun login(
        @Body parameters: LoginParameters
    ): LoginResponse

    @POST("/product")
    suspend fun createProduct(
        @Body parameters: CreateProductParameters
    )

    @GET("/product")
    suspend fun getProducts(): List<ProductResponse>

    @DELETE("/product/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    )

    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    )

    @GET("/supply")
    suspend fun getSupplies(): List<SupplyResponse>

    @PUT("/supply")
    suspend fun updateSupply(
        @Body parameters: UpdateSupplyParameters
    )

    @DELETE("/supply/{id}")
    suspend fun deleteSupply(
        @Path("id") id: Int
    )

    @POST("/unit")
    suspend fun createUnit(
        @Body parameters: CreateUnitParameters
    )

    @GET("/unit")
    suspend fun getUnits(): List<UnitResponse>
}
