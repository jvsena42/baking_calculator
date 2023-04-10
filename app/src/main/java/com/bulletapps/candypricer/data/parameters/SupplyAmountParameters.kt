package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class SupplyAmountParameters(
    @SerializedName("supply_id")
    var supplyId: Int,
    @SerializedName("quantity")
    var quantity: Double,
)
