package com.bulletapps.candypricer.data.api

import com.bulletapps.candypricer.data.parameters.*
import com.bulletapps.candypricer.data.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApi {
    @POST("/product")
    suspend fun createProduct(
        @Body parameters: CreateProductParameters
    ): ProductResponse

    @GET("/product")
    suspend fun getProducts(): List<ProductResponse>
}
