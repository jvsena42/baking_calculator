package com.bulletapps.candypricer.domain.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("admin")
    val isAdmin: Boolean,
    @SerializedName("expiration_date")
    val expirationDate: String,
    @SerializedName("active")
    val isActive: Boolean,
)

