package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import javax.inject.Inject

class ValidatePasswordConfirmationUseCaseImpl @Inject constructor() : ValidatePasswordConfirmationUseCase {
    override suspend fun invoke(password: String, passwordConfirmation: String): Resource<Unit> {
        return if (password == passwordConfirmation) {
            Resource.Success(Unit)
        } else {
            Resource.Error(UiText.StringResource(R.string.error_confirm_password))
        }
    }
}