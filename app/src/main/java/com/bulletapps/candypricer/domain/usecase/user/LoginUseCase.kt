package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.parameters.LoginParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: CandyPricerRepository) {
    suspend operator fun invoke(parameters: LoginParameters) = repository.login(parameters)
}