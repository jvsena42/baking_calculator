package com.bulletapps.candypricer.domain.usecase.unit

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class GetUnitsUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(isRefresh: Boolean = false) = repository.getUnits(isRefresh)
}