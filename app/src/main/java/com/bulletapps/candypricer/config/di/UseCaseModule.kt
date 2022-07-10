package com.bulletapps.candypricer.config.di

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.domain.usecase.*
import com.bulletapps.candypricer.domain.usecase.inputValidation.*
import com.bulletapps.candypricer.domain.usecase.user.CreateUserUseCase
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
    fun providesCreateSupplyUseCase(repository: CandyPricerRepository) : CreateSupplyUseCase {
        return CreateSupplyUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeleteSupplyUseCaseUseCase(repository: CandyPricerRepository) : DeleteSupplyUseCase {
        return DeleteSupplyUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun providesGetAllSuppliesUseCaseUseCase(repository: CandyPricerRepository) : GetAllSuppliesUseCase {
        return GetAllSuppliesUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun providesGetSupplyUseCaseUseCase(repository: CandyPricerRepository) : GetSupplyUseCase {
        return GetSupplyUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun providesUpdateSupplyUseCaseImplUseCase(repository: CandyPricerRepository) : UpdateSupplyUseCase {
        return UpdateSupplyUseCaseImpl(repository)
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
}