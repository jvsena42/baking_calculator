package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class GetAllSuppliesUseCaseImpl @Inject constructor(
    private val repository: CandyPricerRepository
) : GetAllSuppliesUseCase {
    override suspend fun invoke(): List<Supply> = repository.getAllSupplies()
}