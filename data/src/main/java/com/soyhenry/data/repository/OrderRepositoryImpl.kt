package com.soyhenry.data.repository

import com.soyhenry.data.remote.dto.OrderDto
import com.soyhenry.core.domain.Order
import com.soyhenry.core.mappers.toDomain
import com.soyhenry.data.local.datasource.OrderLocalDataSource
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.mappers.toOrderEntity
import com.soyhenry.data.mappers.toOrderItemEntities
import com.soyhenry.data.remote.datasource.OrderRemoteDataSource
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderRemoteDataSource,
    private val local: OrderLocalDataSource
) : OrderRepository {
    override suspend fun createOrder(order: OrderDto) {
        remote.createOrder(order)
    }

    override suspend fun getOrders(refreshData: Boolean): List<Order> {
        return if (refreshData) {
            val remoteOrders = remote.getOrders()
            val orderEntities = remoteOrders.map { it.toOrderEntity() }
            val orderItems = remoteOrders.flatMap { it.toOrderItemEntities() }

            local.updateOrders(orderEntities, orderItems)
            //orderEntities.map { it.toDomain() }
            remoteOrders.map { it.toDomain() }
        } else {
            local.getOrders().map { it.toDomain() }
        }
    }
}
