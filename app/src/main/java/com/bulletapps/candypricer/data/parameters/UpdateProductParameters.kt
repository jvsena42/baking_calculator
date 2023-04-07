package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class UpdateProductParameters(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("quantity")
    var quantity: Double,
    @SerializedName("supply_amount")
    val supplies: List<SupplyAmount>,
    @SerializedName("profit_margin")
    var profitMargin: Double,
    @SerializedName("labor_work")
    var laborValue: Double,
    @SerializedName("variable_expenses")
    var variableExpenses: Double,
    @SerializedName("unit_id")
    var unitId: Int,
) {
    data class SupplyAmount(
        @SerializedName("id")
        var supplyId: Int,
        @SerializedName("quantity")
        var quantity: Double,
    )
}
