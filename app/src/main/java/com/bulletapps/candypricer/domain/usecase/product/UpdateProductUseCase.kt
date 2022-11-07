package com.bulletapps.candypricer.domain.usecase.product

import com.bulletapps.candypricer.data.parameters.UpdateProductParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        unitId: Int,
        quantity: Double,
        suppliesId: List<Int>,
        profitMargin: Double,
        laborValue: Double,
        variableExpenses: Double,
        amountQuantitySupply: List<Double>,
    ) = repository.updateProduct(
        UpdateProductParameters(
            id,
            name,
            unitId,
            quantity,
            suppliesId,
            profitMargin,
            laborValue,
            variableExpenses,
            amountQuantitySupply
        )
    )
}