package com.bulletapps.candypricer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplies")
data class Supply(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Double,
    val unitType: UnitType
)