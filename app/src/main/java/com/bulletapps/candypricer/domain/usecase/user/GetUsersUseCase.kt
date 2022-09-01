package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: CandyPricerRepository) {

    suspend operator fun invoke() =  repository.getUsers()
}