package com.bulletapps.candypricer.domain.model

data class SupplyModel(
    val id: Int,
    val name: String,
    val quantity: Double,
    val value: Double,
    val unit: UnitModel
)
