package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("unit") val unit: UnitResponse?,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("profit_margin") val profitMargin: Double?,
    @SerializedName("labor_work") val laborValue: Double?,
    @SerializedName("variable_expenses") val variableExpenses: Double?,
    @SerializedName("supplies") val supplies: List<SupplyResponse>,
    @SerializedName("total_spends_value") val totalSpendsValue: Double?,
    @SerializedName("unit_sale_value") val unitSaleValue: Double?,
    @SerializedName("total_sale_value") val totalSaleValue: Double?,
    @SerializedName("amount_quantity_supply") val amountQuantitySupply: List<Double>,
)

