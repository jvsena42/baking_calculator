package com.bulletapps.candypricer.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CandyPricerApi {
    @GET("v2/top-headlines")
    suspend fun getSupplies(
        @Query("sources") sources: String,
        @Query("page") page: Int,
    )
}
