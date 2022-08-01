package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.domain.usecase.inputValidation.*
import com.bulletapps.candypricer.domain.usecase.supply.CreateSupplyUseCase
import com.bulletapps.candypricer.domain.usecase.supply.GetAllSuppliesUseCase
import com.bulletapps.candypricer.domain.usecase.unit.CreateUnitUseCase
import com.bulletapps.candypricer.domain.usecase.unit.GetUnitsUseCase
import com.bulletapps.candypricer.domain.usecase.user.CreateUserUseCase
import com.bulletapps.candypricer.domain.usecase.user.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesCreateUserUseCase(repository: CandyPricerRepository) : CreateUserUseCase {
        return CreateUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetUserUseCase(repository: CandyPricerRepository) : GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesCreateSupplyUseCase(repository: CandyPricerRepository) : CreateSupplyUseCase {
        return CreateSupplyUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetAllSuppliesUseCaseUseCase(repository: CandyPricerRepository) : GetAllSuppliesUseCase {
        return GetAllSuppliesUseCase(repository)
    }


    @Singleton
    @Provides
    fun providesCreateUnitUseCaseUseCase(repository: CandyPricerRepository) : CreateUnitUseCase {
        return CreateUnitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetUnitsUseCaseUseCase(repository: CandyPricerRepository) : GetUnitsUseCase {
        return GetUnitsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSubmitEmailUseCase() : SubmitEmailUseCase {
        return SubmitEmailUseCaseImpl()
    }

    @Singleton
    @Provides
    fun providesSubmitPasswordUseCase() : SubmitPasswordUseCase {
        return SubmitPasswordUseCaseImpl()
    }

    @Singleton
    @Provides
    fun providesValidatePasswordConfirmationUseCase() : ValidatePasswordConfirmationUseCase {
        return ValidatePasswordConfirmationUseCaseImpl()
    }

    @Singleton
    @Provides
    fun providesValidateEmptyTextUseCase() : ValidateEmptyTextUseCase {
        return ValidateEmptyTextUseCaseImpl()
    }

    @Singleton
    @Provides
    fun providesValidateEmptyList() : ValidateEmptyListUseCase {
        return ValidateEmptyListUseCase()
    }
}