package com.soyhenry.feature.orders.di

import com.soyhenry.data.remote.api.OrderService
import com.soyhenry.data.repository.OrderRepository
import com.soyhenry.data.repository.OrderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrdersModule {

    @Provides
    fun provideOrderApiService(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(apiService: OrderService): OrderRepository {
        return OrderRepositoryImpl(apiService)
    }
}
