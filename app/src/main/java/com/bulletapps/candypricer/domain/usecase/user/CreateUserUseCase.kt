package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.response.LoginResponse
import com.bulletapps.candypricer.presentation.util.filterNumbers
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository: CandyPricerRepository) {
    suspend operator fun invoke(parameters: CreateUserParameters): Resource<LoginResponse> {
        val cleanPhone = parameters.copy(phone = parameters.phone.filterNumbers())
        return repository.createUser(cleanPhone)
    }
}