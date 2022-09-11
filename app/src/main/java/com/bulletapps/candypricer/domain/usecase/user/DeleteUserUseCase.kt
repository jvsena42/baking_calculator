package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: CandyPricerRepository) {

    suspend operator fun invoke() =  repository.deleteUser()
}