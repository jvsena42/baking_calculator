package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SupplyApi {
    @POST("/supply")
    suspend fun createSupply(
        @Body parameters: CreateSupplyParameters
    ): SupplyResponse

    @GET("/supply")
    suspend fun getSupplies(): List<SupplyResponse>
}
