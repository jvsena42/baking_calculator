package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserResponse(
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
    val expirationDate: Date,
)

