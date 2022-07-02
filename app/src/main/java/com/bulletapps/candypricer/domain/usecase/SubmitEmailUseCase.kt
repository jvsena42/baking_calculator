package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.config.Resource

interface SubmitEmailUseCase {
    operator fun invoke (email: String): Resource<Unit>
}