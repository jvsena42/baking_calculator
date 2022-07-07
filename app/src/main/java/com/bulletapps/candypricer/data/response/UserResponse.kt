package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val email: String,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
    @SerializedName("is_premium")
    val isPremium: Boolean
)

