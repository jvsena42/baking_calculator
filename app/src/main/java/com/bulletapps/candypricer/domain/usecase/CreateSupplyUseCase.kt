package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.domain.model.Supply

interface CreateSupplyUseCase {
    suspend operator fun invoke(supply: Supply)
}