package com.bulletapps.candypricer.data.response

import com.google.gson.annotations.SerializedName

data class UnitResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
