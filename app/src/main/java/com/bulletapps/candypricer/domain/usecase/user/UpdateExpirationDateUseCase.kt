package com.bulletapps.candypricer.domain.usecase.user

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.data.parameters.UpdateExpirationDateParameters
import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.presentation.util.DateConstant
import com.bulletapps.candypricer.presentation.util.format
import com.bulletapps.candypricer.presentation.util.toDate
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import javax.inject.Inject

class UpdateExpirationDateUseCase @Inject constructor(private val repository: CandyPricerRepository) {
    //date in the ddMMyyyy format
    //TODO ADD EMPTY FIELD VALDIATION
    suspend operator fun invoke(id: Int, date: String): Resource<Unit> {
        val formattedDate = date.toDate(DateConstant.DAY_MONTH_YEAR_CLEAR_FORMAT).format(
            DateConstant.BACKEND_FORMAT
        )
        return repository.updateExpirationDate(id, UpdateExpirationDateParameters(formattedDate))
    }
}