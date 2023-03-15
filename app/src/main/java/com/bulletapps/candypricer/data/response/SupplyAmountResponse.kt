package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class SupplyAmountResponse(
    @SerializedName("id")
    var supplyId: Int,
    @SerializedName("quantity")
    var quantity: Double,
    @SerializedName("name")
    var name: Double,
)
