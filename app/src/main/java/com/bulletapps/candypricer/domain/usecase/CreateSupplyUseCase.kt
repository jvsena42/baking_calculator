package com.bulletapps.candypricer.domain.usecase

import android.net.Uri
import com.bulletapps.candypricer.data.model.Supply

interface CreateSupplyUseCase {
    suspend operator fun invoke(supply: Supply)
}