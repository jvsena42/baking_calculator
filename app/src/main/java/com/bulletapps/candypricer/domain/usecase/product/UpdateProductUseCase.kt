package com.bulletapps.candypricer.domain.usecase.product

import com.bulletapps.candypricer.data.parameters.UpdateProductParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(product: UpdateProductParameters) = repository.updateProduct(product)
}