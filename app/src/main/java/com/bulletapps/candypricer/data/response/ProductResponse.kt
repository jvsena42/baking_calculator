package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("quantity") val quantity: Double?,
    @SerializedName("supply_amount") val supplies: List<SupplyAmountResponse>?,
    @SerializedName("profit_margin") val profitMargin: Double?,
    @SerializedName("labor_work") val laborValue: Double?,
    @SerializedName("variable_expenses") val variableExpenses: Double?,
    @SerializedName("unit") val unit: UnitResponse?,
    @SerializedName("total_spends_value") val totalSpendsValue: Double?,
    @SerializedName("unit_sale_value") val unitSaleValue: Double?,
    @SerializedName("total_sale_value") val totalSaleValue: Double?,
)

