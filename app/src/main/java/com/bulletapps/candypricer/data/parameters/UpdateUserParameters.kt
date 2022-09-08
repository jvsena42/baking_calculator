package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class UpdateUserParameters(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
    @SerializedName("expiration_date")
    val expirationDate: String,
)

