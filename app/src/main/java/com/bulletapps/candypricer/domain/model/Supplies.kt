package com.bulletapps.candypricer.domain.model

data class Supplies(
    val id: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val quantity: Double,
    val unitType: UnitType
)