package com.soyhenry.data.repository

import com.soyhenry.data.remote.api.OrderService
import com.soyhenry.data.remote.dto.OrderRequestDto
import com.soyhenry.core.domain.Order
import com.soyhenry.data.mappers.toDomain
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrderService
) : OrderRepository {
    override suspend fun createOrder(order: OrderRequestDto) {
        remote.createOrder(order)
    }

    override suspend fun getOrders(): List<Order> {
        val remoteOrders = remote.getOrders()
        return remoteOrders.map { it.toDomain() }
    }
}
