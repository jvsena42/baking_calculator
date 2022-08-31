package com.bulletapps.candypricer.domain.usecase.product

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(supplyId: Int) = repository.deleteProduct(supplyId)
}