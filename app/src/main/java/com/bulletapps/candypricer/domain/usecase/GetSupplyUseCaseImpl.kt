package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.domain.model.Supply
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class GetSupplyUseCaseImpl @Inject constructor(
    private val repository: CandyPricerRepository
) : GetSupplyUseCase {

    override suspend fun invoke(id: Int): Supply? = repository.getSupply(id)
}