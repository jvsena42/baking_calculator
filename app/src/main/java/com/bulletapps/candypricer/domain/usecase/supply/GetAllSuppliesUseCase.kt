package com.bulletapps.candypricer.domain.usecase.supply

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class GetAllSuppliesUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke() = repository.getSupplies()
}