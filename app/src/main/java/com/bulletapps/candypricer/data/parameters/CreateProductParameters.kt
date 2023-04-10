package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class CreateProductParameters(
    @SerializedName("name")
    var name: String,
    @SerializedName("quantity")
    var quantity: Double,
    @SerializedName("supply_amount")
    val suppliesAmount: List<SupplyAmountParameters>,
    @SerializedName("profit_margin")
    var profitMargin: Double,
    @SerializedName("labor_work")
    var laborValue: Double,
    @SerializedName("variable_expenses")
    var variableExpenses: Double,
    @SerializedName("unit_id")
    var unitId: Int,
)
