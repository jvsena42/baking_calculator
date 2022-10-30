package com.bulletapps.candypricer.domain.model

data class MenuItemModel(
    val id: Int,
    val name: String,
    val quantity: String,
    val unit: String = ""
)
