package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.config.Resource

interface SubmitPasswordUseCase {
    suspend operator  fun invoke (password: String): Resource<Unit>
}