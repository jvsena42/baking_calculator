package com.bulletapps.candypricer.domain.usecase.preferences

import com.bulletapps.candypricer.data.repository.UserPreferencesRepositoryImpl
import javax.inject.Inject

class GetLoginDataUseCase @Inject constructor(
    private val preferencesRepository: UserPreferencesRepositoryImpl
) {
    suspend operator fun invoke() = preferencesRepository.fetchLoginResponse()
}