package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.entities.UserEntity
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

fun UserEntity?.toUserModel() = UserModel (
    id = this?.id.orNegative(),
    name = this?.name.orEmpty(),
    phone = this?.phone.orEmpty(),
    email = this?.email.orEmpty(),
    isAdmin = this?.isAdmin.orFalse(),
    expirationDate = this?.expirationDate.orEmpty(),
    isActive = this?.isActive.orFalse()
        )

fun UserModel.toUserEntity() = UserEntity (
    id = id,
    name = name,
    phone = phone,
    email = email,
    isAdmin = isAdmin,
    expirationDate = expirationDate,
    isActive = isActive
        )

fun List<UserResponse>?.toUserModelList() = this?.map { it.toUserModel() }.orEmpty()