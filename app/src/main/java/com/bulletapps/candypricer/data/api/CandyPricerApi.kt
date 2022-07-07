package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CandyPricerApi {
    @POST("/unit")
    suspend fun createUnit(
        @Body parameters: CreateUnitParameters
    ) //todo add return

    @GET("/unit")
    suspend fun getUnits(
        @Query("key") key: String,
        @Query("value") value: String,
        @Query("disabled") disabled: Boolean,
    ) //todo add return

    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    ) //todo add return

    @GET("/supply")
    suspend fun getSupplies() //todo add return

    @POST("/product")
    suspend fun createProduct(
        @Body parameters: CreateProductParameters
    ) //todo add return

    @GET("/product")
    suspend fun getProducts() //todo add return

    @POST("/user")
    suspend fun createUser(
        @Body parameters: CreateUserParameters
    )

    @POST("/login")
    suspend fun login(
        @Body parameters: LoginParameters
    ): LoginResponse
}
