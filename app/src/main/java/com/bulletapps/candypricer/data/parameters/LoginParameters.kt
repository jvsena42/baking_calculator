package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class LoginParameters(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
)
