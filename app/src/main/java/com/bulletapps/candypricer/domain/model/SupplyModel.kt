package com.bulletapps.candypricer.domain.model

data class SupplyModel(
    val id: Int,
    val name: String,
    val quantity: String,
    val value: String,
    val unit: UnitModel
)
