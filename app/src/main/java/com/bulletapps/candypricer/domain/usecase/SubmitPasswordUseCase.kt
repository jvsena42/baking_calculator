package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.config.Resource

interface SubmitPasswordUseCase {
    operator fun invoke (password: String): Resource<Unit>
}