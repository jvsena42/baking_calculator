package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.config.Resource

interface ValidateEmptyTextUseCase {
    suspend operator fun invoke (text: String): Resource<Unit>
}