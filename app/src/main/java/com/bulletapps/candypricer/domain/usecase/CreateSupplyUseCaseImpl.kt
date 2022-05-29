package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class CreateSupplyUseCaseImpl @Inject constructor(
    private val repository: CandyPricerRepository
) : CreateSupplyUseCase {
    override suspend fun invoke(supply: Supply) = repository.createSupply(supply)

}