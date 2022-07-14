package com.bulletapps.candypricer.domain.usecase.inputValidation

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import javax.inject.Inject

class SubmitPasswordUseCaseImpl @Inject constructor(): SubmitPasswordUseCase {
    override suspend fun invoke(password: String): Resource<Unit> {
        if(password.length < 6) {
            return Resource.Error(
                UiText.StringResource(R.string.error_invalid_password)
            )
        }
        return Resource.Success(Unit)
    }
}