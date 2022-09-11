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
    ): UserResponse // TODO REMOVE RETURN

    @DELETE("/user")
    suspend fun deleteUser(): UserResponse // TODO REMOVE RETURN

    @GET("/users")
    suspend fun getUsers(): List<UserResponse>

    @POST("/login")
    suspend fun login(
        @Body parameters: LoginParameters
    ): LoginResponse

    @POST("/product")
    suspend fun createProduct(
        @Body parameters: CreateProductParameters
    ): ProductResponse // TODO REMOVE RETURN

    @GET("/product")
    suspend fun getProducts(): List<ProductResponse>

    @DELETE("/product/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): ProductResponse // TODO REMOVE RETURN

    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    )

    @GET("/supply")
    suspend fun getSupplies(): List<SupplyResponse>

    @PUT("/supply")
    suspend fun updateSupply(
        @Body parameters: UpdateSupplyParameters
    ): SupplyResponse // TODO REMOVE RETURN

    @DELETE("/supply/{id}")
    suspend fun deleteSupply(
        @Path("id") id: Int
    ): CreateSupplyParameters // TODO REMOVE RETURN

    @POST("/unit")
    suspend fun createUnit(
        @Body parameters: CreateUnitParameters
    ): UnitResponse // TODO REMOVE RETURN

    @GET("/unit")
    suspend fun getUnits(): List<UnitResponse>
}
