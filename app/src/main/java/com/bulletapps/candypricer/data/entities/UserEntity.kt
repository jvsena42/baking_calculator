package com.bulletapps.candypricer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String?,
    val phone: String?,
    val email: String?,
    val isAdmin: Boolean?,
    val expirationDate: String?,
    val isActive: Boolean?,
)

