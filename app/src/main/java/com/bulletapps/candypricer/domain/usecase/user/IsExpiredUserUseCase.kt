package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.presentation.util.toDate
import java.util.*
import javax.inject.Inject

class IsExpiredUserUseCase @Inject constructor() {
    suspend operator fun invoke(expirationDateString: String): Boolean  {
        val currentDate = Calendar.getInstance().time
        return expirationDateString.toDate() <= currentDate
    }
}