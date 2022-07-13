package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class CreateProductParameters(
    @SerializedName("name")
    var name: String,
    @SerializedName("unit_id")
    var unitId: Int,
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("supplies")
    var suppliesId: List<Int>,
    @SerializedName("profit_margin")
    var profitMargin: Double,
    @SerializedName("labor_work")
    var laborValue: Double,
    @SerializedName("variable_expenses")
    var variableExpenses: Double,
)