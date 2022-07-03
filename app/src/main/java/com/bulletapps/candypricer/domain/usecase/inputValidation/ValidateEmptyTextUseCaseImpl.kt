package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import javax.inject.Inject

class ValidateEmptyTextUseCaseImpl @Inject constructor() : ValidateEmptyTextUseCase {
    override suspend fun invoke(text: String): Resource<Unit> {
        return if (text.isNotEmpty()) {
            Resource.Success(null)
        } else {
            Resource.Error(UiText.StringResource(
                R.string.mandatory_field))
        }
    }
}