package com.bulletapps.candypricer.domain.usecase.supply

import com.bulletapps.candypricer.data.parameters.UpdateSupplyParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class UpdateSupplyUseCase @Inject constructor(
    private val repository: CandyPricerRepository
) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        quantity: Double,
        price: Double,
        unitId: Int,
    ) = repository.updateSupply(
        UpdateSupplyParameters(
            id = id,
            name = name,
            quantity = quantity,
            price = price,
            unitId = unitId
        )
    )
}