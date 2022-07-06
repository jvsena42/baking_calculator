package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class CreateUserParameters(
    @SerializedName("name")
    var name: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("email")
    var email: String,
)
