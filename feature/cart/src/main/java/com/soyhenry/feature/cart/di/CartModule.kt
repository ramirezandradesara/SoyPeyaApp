package com.soyhenry.feature.cart.di

import com.soyhenry.data.local.dao.CartItemDao
import com.soyhenry.data.local.datasource.CartItemLocalDataSource
import com.soyhenry.data.local.datasource.CartItemLocalDataSourceImpl
import com.soyhenry.data.repository.CartItemRepository
import com.soyhenry.data.repository.CartItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    fun provideCartItemDataSource(
        cartItemDao: CartItemDao
    ): CartItemLocalDataSource {
        return CartItemLocalDataSourceImpl(cartItemDao)
    }

    @Provides
    @Singleton
    fun provideCartItemRepository(
        localDataSource: CartItemLocalDataSource
    ): CartItemRepository {
        return CartItemRepositoryImpl(localDataSource)
    }
}
