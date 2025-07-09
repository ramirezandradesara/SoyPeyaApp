package com.soyhenryfeature.products.di

import com.soyhenry.data.local.dao.ProductDao
import com.soyhenry.data.local.datasource.products.ProductLocalDataSource
import com.soyhenry.data.local.datasource.products.ProductLocalDataSourceImpl
import com.soyhenry.data.remote.api.ProductsService
import com.soyhenry.data.remote.datasource.products.ProductRemoteDataSource
import com.soyhenry.data.remote.datasource.products.ProductRemoteDataSourceImpl
import com.soyhenry.data.repository.ProductRepositoryImpl
import com.soyhenry.data.repository.ProductsRepository
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
    fun provideRemoteDataSource(
        productsService: ProductsService
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(productsService)
    }

    @Provides
    fun provideLocalDataSource(
        productDao: ProductDao
    ): ProductLocalDataSource {
        return ProductLocalDataSourceImpl(productDao)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        remoteDataSource: ProductRemoteDataSource,
        localDataSource: ProductLocalDataSource
    ): ProductsRepository {
        return ProductRepositoryImpl(remoteDataSource, localDataSource)
    }
}
