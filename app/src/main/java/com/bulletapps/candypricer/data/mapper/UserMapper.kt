package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.domain.model.UserModel
import com.bulletapps.candypricer.presentation.util.orFalse
import com.bulletapps.candypricer.presentation.util.orNegative

fun UserResponse.toUserModel() = UserModel (
    id = id.orNegative(),
    name = name.orEmpty(),
    phone = phone.orEmpty(),
    email = email.orEmpty(),
    isAdmin = isAdmin.orFalse(),
    expirationDate = expirationDate.orEmpty(),
    isActive = isActive.orFalse()
        )

fun List<UserResponse>?.toUserModelList() = this?.map { it.toUserModel() }.orEmpty()