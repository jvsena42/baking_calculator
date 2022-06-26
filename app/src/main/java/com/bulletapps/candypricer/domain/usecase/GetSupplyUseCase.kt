package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.domain.model.Supply

interface GetSupplyUseCase {
    suspend operator fun invoke(id: Int) : Supply?
}