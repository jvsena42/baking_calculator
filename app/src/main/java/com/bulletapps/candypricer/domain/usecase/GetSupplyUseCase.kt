package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply

interface GetSupplyUseCase {
    suspend operator fun invoke(id: Int) : Supply?
}