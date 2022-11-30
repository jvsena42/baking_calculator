package com.bulletapps.candypricer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "units"
)
data class UnitEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String?
)
