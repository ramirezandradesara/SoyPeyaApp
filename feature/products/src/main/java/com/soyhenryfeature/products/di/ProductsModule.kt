package com.soyhenryfeature.products.di

import com.soyhenry.core.model.database.dao.ProductDao
import com.soyhenry.core.repository.ProductDataSource
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
    fun provideProductDataSource(): ProductDataSource {
        return ProductDataSource()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        productDao: ProductDao,
        productDataSource: ProductDataSource
    ): ProductsRepository {
        return ProductsRepositoryImpl(productDao, productDataSource)
    }
}
