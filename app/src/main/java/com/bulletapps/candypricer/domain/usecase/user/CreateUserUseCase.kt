package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.response.UserResponse

interface CreateUserUseCase {
    suspend operator fun invoke(parameters: CreateUserParameters) : UserResponse
}