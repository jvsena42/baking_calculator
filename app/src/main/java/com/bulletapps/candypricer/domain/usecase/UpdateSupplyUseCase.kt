package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply

interface UpdateSupplyUseCase {
    suspend operator fun invoke (supply: Supply)
}