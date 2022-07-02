package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import javax.inject.Inject

class SubmitEmailUseCaseImpl @Inject constructor(): SubmitEmailUseCase {
    override suspend fun invoke(email: String): Resource<Unit> {
        if(!email.contains("@")) {
            return Resource.Error(
                UiText.StringResource(R.string.error_invalid_email)
            )
        }
        return Resource.Success(null)
    }
}