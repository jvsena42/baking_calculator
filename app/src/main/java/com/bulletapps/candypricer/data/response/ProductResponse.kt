package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("name") val name: String,
    @SerializedName("unit") val unit: UnitResponse?,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("profit_margin") val profitMargin: Double?,
    @SerializedName("labor_work") val laborValue: Double?,
    @SerializedName("variable_expenses") val variableExpenses: Double?,
    @SerializedName("value") val price: Double?,
    @SerializedName("supplies") val suppliesId: List<SupplyResponse>,
)

