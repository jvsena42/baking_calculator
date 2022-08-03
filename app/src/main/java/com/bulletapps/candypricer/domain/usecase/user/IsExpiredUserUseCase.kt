package com.bulletapps.candypricer.domain.usecase.user

import java.util.*
import javax.inject.Inject

class IsExpiredUserUseCase @Inject constructor() {
    suspend operator fun invoke(expirationDate: Date): Boolean  {
        val currentDate = Calendar.getInstance().time
        return expirationDate <= currentDate
    }
}