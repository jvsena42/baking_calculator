package com.bulletapps.candypricer.data.api

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
    suspend fun createSupply()

    @POST("/user")
    suspend fun createUser()

    @POST("/login")
    suspend fun login()
}
