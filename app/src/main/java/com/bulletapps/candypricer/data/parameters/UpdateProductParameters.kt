package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class UpdateProductParameters(
    @SerializedName("name")
    var name: String,
    @SerializedName("quantity")
    var quantity: Double,
    @SerializedName("profit_margin")
    var profitMargin: Double,
    @SerializedName("labor_work")
    var laborValue: Double,
    @SerializedName("variable_expenses")
    var variableExpenses: Double,
    @SerializedName("unit_id")
    var unitId: Int,
)