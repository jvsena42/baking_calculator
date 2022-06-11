package com.bulletapps.candypricer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplies")
data class Supply(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Double,
    val unitType: String
)