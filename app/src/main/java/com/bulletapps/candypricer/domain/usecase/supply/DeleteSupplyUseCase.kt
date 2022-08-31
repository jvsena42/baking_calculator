package com.bulletapps.candypricer.domain.usecase.supply

import com.bulletapps.candypricer.data.parameters.CreateSupplyParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class DeleteSupplyUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(supplyId: Int) = repository.deleteSupply(supplyId)
}