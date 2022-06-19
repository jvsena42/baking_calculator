package com.bulletapps.candypricer.data.model

data class Product(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val unitType: String,
    val quantity: Double = 0.0,
    val componentIds: List<String>
)
