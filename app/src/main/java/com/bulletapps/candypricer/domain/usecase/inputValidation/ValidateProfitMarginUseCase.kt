package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.presentation.util.formatDouble
import com.bulletapps.candypricer.presentation.util.removeLetters
import javax.inject.Inject

class ValidateProfitMarginUseCase @Inject constructor() : ValidateEmptyTextUseCase {
    override suspend fun invoke(text: String): Resource<Unit> {
        val string = text.removeLetters()
        return if (string.isNotEmpty() && string.formatDouble() >= 0) {
            Resource.Success(Unit)
        } else {
            Resource.Error(
                UiText.StringResource(
                    R.string.error_profit_margin
                )
            )
        }
    }
}