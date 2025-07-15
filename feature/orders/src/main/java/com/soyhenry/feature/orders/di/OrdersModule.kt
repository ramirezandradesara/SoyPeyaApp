package com.soyhenry.feature.orders.di

import com.soyhenry.data.local.dao.OrderDao
import com.soyhenry.data.local.dao.OrderItemDao
import com.soyhenry.data.local.datasource.OrderLocalDataSource
import com.soyhenry.data.local.datasource.OrderLocalDataSourceImpl
import com.soyhenry.data.remote.api.OrderService
import com.soyhenry.data.remote.datasource.OrderRemoteDataSource
import com.soyhenry.data.remote.datasource.OrderRemoteDataSourceImpl
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
    fun provideOrderService(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    fun provideRemoteOrderDataSource(
        service: OrderService
    ): OrderRemoteDataSource {
        return OrderRemoteDataSourceImpl(service)
    }

    @Provides
    fun provideLocalOrderDataSource(
        orderDao: OrderDao,
        orderItemDao: OrderItemDao
    ): OrderLocalDataSource {
        return OrderLocalDataSourceImpl(orderDao, orderItemDao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        remote: OrderRemoteDataSource,
        local: OrderLocalDataSource
    ): OrderRepository {
        return OrderRepositoryImpl(remote, local)
    }
}
