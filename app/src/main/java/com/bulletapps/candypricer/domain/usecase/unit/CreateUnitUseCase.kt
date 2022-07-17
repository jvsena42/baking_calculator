package com.bulletapps.candypricer.domain.usecase.unit

import com.bulletapps.candypricer.data.parameters.CreateUnitParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class CreateUnitUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(supply: CreateUnitParameters) = repository.createUnit(supply)
}