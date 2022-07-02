package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText

class SubmitEmailUseCaseImpl: SubmitEmailUseCase {
    override fun invoke(email: String): Resource<Unit> {
        if(!email.contains("@")) {
            return Resource.Error(
                UiText.StringResource(R.string.error_invalid_email)
            )
        }
        return Resource.Success(null)
    }
}