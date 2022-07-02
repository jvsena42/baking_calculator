package com.bulletapps.candypricer.domain.di

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.domain.usecase.*
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitEmailUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitEmailUseCaseImpl
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitPasswordUseCase
import com.bulletapps.candypricer.domain.usecase.inputValidation.SubmitPasswordUseCaseImpl
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
    fun providesCreateSupplyUseCase(repository: CandyPricerRepository) : CreateSupplyUseCase {
        return CreateSupplyUseCaseImpl(repository)
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
    fun providesSubmitEmailUseCaseImpl() : SubmitEmailUseCase {
        return SubmitEmailUseCaseImpl()
    }

    @Singleton
    @Provides
    fun providesSubmitPasswordUseCaseImpl() : SubmitPasswordUseCase {
        return SubmitPasswordUseCaseImpl()
    }
}