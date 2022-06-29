package com.bulletapps.candypricer.domain.model

import java.util.*

data class User(
    val name: String,
    val expirationDate: Calendar,
    val phone: String
)