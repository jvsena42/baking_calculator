package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CandyPricerApi {
    @POST("/user")
    suspend fun createUser(
        @Body parameters: CreateUserParameters
    ): UserResponse

    @POST("/login")
    suspend fun login(
        @Body parameters: LoginParameters
    ): LoginResponse
}
