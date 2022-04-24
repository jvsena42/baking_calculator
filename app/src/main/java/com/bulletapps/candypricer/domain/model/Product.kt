package com.bulletapps.candypricer.domain.model

data class Product(
    val id: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val unitType: UnitType,
    val quantity: Double = 0.0,
    val componentIds: List<String>
)
