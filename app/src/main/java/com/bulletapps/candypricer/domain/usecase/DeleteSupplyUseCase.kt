package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply

interface DeleteSupplyUseCase {
    suspend operator fun invoke(supply: Supply)
}