package com.wirebarley.exchange.data.di

import com.wirebarley.exchange.data.repo.CurrencyLayerRepository
import com.wirebarley.exchange.data.repo.CurrencyLayerRepositoryImpl
import com.wirebarley.exchange.data.repo.TestCurrencyLayerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    @RealRepository
    abstract fun provideCurrencyLayerRepository(
        currencyLayerRepositoryImpl: CurrencyLayerRepositoryImpl
    ): CurrencyLayerRepository

    @Binds
    @Singleton
    @TestRepository
    abstract fun provideTestCurrencyLayerRepository(
        currencyLayerRepositoryImpl: TestCurrencyLayerRepositoryImpl
    ): CurrencyLayerRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RealRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TestRepository