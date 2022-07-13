package com.bulletapps.candypricer.domain.usecase.preferences

import com.bulletapps.candypricer.data.repository.UserPreferencesRepositoryImpl
import com.bulletapps.candypricer.data.response.LoginResponse
import javax.inject.Inject

class SaveLoginDataUseCase @Inject constructor(
    private val preferencesRepository: UserPreferencesRepositoryImpl
) {
    suspend fun invoke(loginResponse: LoginResponse) = preferencesRepository.updateLoginResponse(loginResponse)
}