package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("access-token")
    val accessToken: String,
)
