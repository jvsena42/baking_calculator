package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.config.Resource

interface ValidatePasswordConfirmationUseCase {
    suspend operator fun invoke(password: String, passwordConfirmation: String): Resource<Unit>
}