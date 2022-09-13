package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class UpdateExpirationDateParameters(
    @SerializedName("expiration_date")
    val expirationDate: String,
)

