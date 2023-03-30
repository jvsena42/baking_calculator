package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class SupplyAmountResponse(
    @SerializedName("quantity")
    var quantity: Double,
    @SerializedName("supply")
    var supply: SupplyResponse,
)
