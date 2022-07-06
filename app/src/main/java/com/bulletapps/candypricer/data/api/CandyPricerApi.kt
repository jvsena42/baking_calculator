package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.CreateSupplyParameters
import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.parameters.LoginParameters
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CandyPricerApi {
    @GET("/unit")
    suspend fun getUnits(
        @Query("key") key: String,
        @Query("value") value: String,
        @Query("disabled") disabled: Boolean,
    )

    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    )

    @POST("/user")
    suspend fun createUser(
        @Body parameters: CreateUserParameters
    )

    @POST("/login")
    suspend fun login(
        @Body parameters: LoginParameters
    )
}
