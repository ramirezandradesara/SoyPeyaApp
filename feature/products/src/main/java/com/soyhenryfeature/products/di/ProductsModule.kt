package com.soyhenryfeature.products.di

import com.soyhenryfeature.products.data.repository.ProductsRepository
import com.soyhenryfeature.products.data.repository.ProductsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    @Singleton
    fun provideProductsRepository(): ProductsRepository {
        return ProductsRepositoryImpl()
    }
}