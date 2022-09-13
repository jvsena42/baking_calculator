package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.data.parameters.UpdateExpirationDateParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class UpdateExpirationDateUseCase @Inject constructor(private val repository: CandyPricerRepository) {
    suspend operator fun invoke(id:Int, parameters: UpdateExpirationDateParameters) = repository.updateExpirationDate(id, parameters)
}