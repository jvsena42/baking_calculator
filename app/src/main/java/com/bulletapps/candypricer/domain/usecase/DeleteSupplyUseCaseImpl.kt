package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.data.repository.CandyPricerRepository

class DeleteSupplyUseCaseImpl(
    private val repository: CandyPricerRepository
) : DeleteSupplyUseCase {
    override suspend fun invoke(supply: Supply) = repository.deleteSupply(supply)
}