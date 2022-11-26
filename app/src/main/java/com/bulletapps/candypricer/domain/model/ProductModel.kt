package com.bulletapps.candypricer.domain.model

data class ProductModel(
    val id: Int,
    val name: String,
    val unit: UnitModel,
    val quantity: Double,
    val profitMargin: Double,
    val laborValue: Double,
    val variableExpenses: Double,
    val supplies: List<ProductSupplyModel>,
    val totalSpendsValue: Double,
    val unitSaleValue: Double,
    val totalSaleValue: Double,
)

