package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.parameters.CreateSupplyParameters
import com.bulletapps.candypricer.domain.model.Supply
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class CreateSupplyUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend fun invoke(supply: CreateSupplyParameters) = repository.createSupply(supply)
}