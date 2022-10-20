package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import javax.inject.Inject

class ValidateEmptyListUseCase @Inject constructor() {
    suspend operator fun invoke(list: List<Any>): Resource<Unit> {
        return if (list.isNotEmpty()) {
            Resource.Success(Unit)
        } else {
            Resource.Error(
                UiText.StringResource(
                    R.string.mandatory_field
                )
            )
        }
    }
}