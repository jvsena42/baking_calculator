package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class UpdateSupplyUseCaseImpl @Inject constructor(
    private val repository: CandyPricerRepository
) : UpdateSupplyUseCase {
    override suspend fun invoke(supply: Supply) = repository.updateSupply(supply)
}