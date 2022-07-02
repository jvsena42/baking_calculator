package com.bulletapps.candypricer.domain.usecase

import com.bulletapps.candypricer.R
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText

class SubmitPasswordUseCaseImpl: SubmitPasswordUseCase {
    override fun invoke(password: String): Resource<Unit> {
        if(password.length < 6) {
            return Resource.Error(
                UiText.StringResource(R.string.error_invalid_password)
            )
        }
        return Resource.Success(null)
    }
}