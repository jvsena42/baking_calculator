package com.bulletapps.candypricer.domain.usecase.product

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    // TODO VALIDATE NEGATIVE VALUE
    suspend operator fun invoke(supplyId: Int) = repository.deleteProduct(supplyId)
}