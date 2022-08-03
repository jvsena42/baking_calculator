package com.bulletapps.candypricer.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("user_message")
    var userMessage: String? = "",
    @SerializedName("dev_message")
    var devMessage: String? = "",
)