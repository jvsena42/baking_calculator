package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class SupplyResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("value") val value: Double,
    @SerializedName("unit") val unit: UnitResponse?
)
