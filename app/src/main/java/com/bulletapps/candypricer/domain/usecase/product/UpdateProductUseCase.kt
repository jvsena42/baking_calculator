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
        suppliesAmount: List<UpdateProductParameters.SupplyAmount>,
        profitMargin: Double,
        laborValue: Double,
        variableExpenses: Double,
    ) = repository.updateProduct(
        UpdateProductParameters(
            id = id,
            name = name,
            unitId = unitId,
            quantity = quantity,
            supplies = suppliesAmount,
            profitMargin = profitMargin,
            laborValue = laborValue,
            variableExpenses = variableExpenses,
        )
    )
}