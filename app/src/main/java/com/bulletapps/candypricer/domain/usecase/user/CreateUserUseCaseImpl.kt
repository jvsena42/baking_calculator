package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.parameters.CreateUserParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(private val repository: CandyPricerRepository): CreateUserUseCase {
    override suspend fun invoke(parameters: CreateUserParameters) = repository.createUser(parameters)
}