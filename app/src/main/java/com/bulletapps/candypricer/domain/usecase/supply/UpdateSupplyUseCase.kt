package com.bulletapps.candypricer.domain.usecase.supply

import com.bulletapps.candypricer.data.parameters.UpdateSupplyParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.response.SupplyResponse
import javax.inject.Inject

class UpdateSupplyUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(supply: UpdateSupplyParameters) = repository.updateSupply(supply)
}