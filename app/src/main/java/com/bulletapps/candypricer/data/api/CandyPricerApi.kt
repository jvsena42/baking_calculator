package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CandyPricerApi {
    @POST("/user")
    suspend fun createUser(
        @Body parameters: CreateUserParameters
    ): LoginResponse

    @GET("/user")
    suspend fun getUser(): UserResponse

    @DELETE("/user")
    suspend fun deleteUser(): Response<ResponseBody>

    @POST("/user/login")
    suspend fun login(
        @Body parameters: LoginParameters
    ): LoginResponse

    @PUT("/admin/users/{id}")
    suspend fun updateExpirationDate(
        @Path("id") id: Int,
        @Query("expiration_date") expirationDate: String
    ): Response<ResponseBody>

    @GET("/admin/users")
    suspend fun getUsers(): List<UserResponse>

    @DELETE("/admin/user/{id}")
    suspend fun deleteUserAdmin(
        @Path("id") id: Int
    ): Response<ResponseBody>

    @POST("/product")
    suspend fun createProduct(
        @Body parameters: CreateProductParameters
    ): Response<ResponseBody>

    @GET("/product")
    suspend fun getProducts(): List<ProductResponse>

    @PUT("/product/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body parameters: UpdateProductParameters
    ): Response<ResponseBody>

    @DELETE("/product/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): Response<ResponseBody>

    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    ): Response<ResponseBody>

    @GET("/supply")
    suspend fun getSupplies(): List<SupplyResponse>

    @PUT("/supply")
    suspend fun updateSupply(
        @Body parameters: UpdateSupplyParameters
    ): Response<ResponseBody>

    @DELETE("/supply/{id}")
    suspend fun deleteSupply(
        @Path("id") id: Int
    ): Response<ResponseBody>

    @POST("/unit")
    suspend fun createUnit(
        @Body parameters: CreateUnitParameters
    ): Response<ResponseBody>

    @GET("/unit")
    suspend fun getUnits(): List<UnitResponse>
}
