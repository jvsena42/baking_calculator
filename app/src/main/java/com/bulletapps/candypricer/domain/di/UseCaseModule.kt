package com.bulletapps.candypricer.domain.di

import com.bulletapps.candypricer.data.repository.CandyPricerRepository
import com.bulletapps.candypricer.domain.usecase.*
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
}