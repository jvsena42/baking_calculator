package com.bulletapps.candypricer.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("validationError")
    var errorList: List<FieldError>? = emptyList(),
)

data class FieldError(
    @SerializedName("field")
    var field: String? = "",
    @SerializedName("displayMessage")
    var message: String? = "",
)