package com.bulletapps.candypricer.domain.usecase.supply

import com.bulletapps.candypricer.data.parameters.CreateSupplyParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class CreateSupplyUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(supply: CreateSupplyParameters) = repository.createSupply(supply)
}