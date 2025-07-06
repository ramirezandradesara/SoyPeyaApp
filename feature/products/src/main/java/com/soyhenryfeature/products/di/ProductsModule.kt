package com.soyhenryfeature.products.di

import com.soyhenry.data.remote.api.ProductsService
import com.soyhenryfeature.products.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    fun provideProductsService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(productsService: ProductsService): ProductsRepository {
        return ProductsRepositoryImpl(productsService)
    }
}