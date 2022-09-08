package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.parameters.UpdateUserParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.response.LoginResponse
import com.bulletapps.candypricer.data.response.UserResponse
import com.bulletapps.candypricer.presentation.util.filterNumbers
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val repository: CandyPricerRepository) {
    suspend operator fun invoke(parameters: UpdateUserParameters): Resource<UserResponse> {
        return repository.updateUser(parameters)
    }
}