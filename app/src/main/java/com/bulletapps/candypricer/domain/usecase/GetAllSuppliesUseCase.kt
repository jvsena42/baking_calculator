package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.data.model.Supply

interface GetAllSuppliesUseCase {
    suspend operator fun invoke() : List<Supply>
}