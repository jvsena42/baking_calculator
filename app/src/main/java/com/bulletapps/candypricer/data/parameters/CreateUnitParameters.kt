package com.bulletapps.candypricer.data.parameters

import com.google.gson.annotations.SerializedName

data class CreateUnitParameters(
    @SerializedName("name")
    var name: String
)
