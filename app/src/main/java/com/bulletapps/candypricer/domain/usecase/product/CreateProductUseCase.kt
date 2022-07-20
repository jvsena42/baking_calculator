package com.bulletapps.candypricer.domain.usecase.product

import com.bulletapps.candypricer.data.parameters.CreateProductParameters
import com.bulletapps.candypricer.data.parameters.CreateSupplyParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(product: CreateProductParameters) = repository.createProduct(product)
}