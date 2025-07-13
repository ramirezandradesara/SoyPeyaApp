package com.soyhenry.data.repository

import com.soyhenry.core.domain.Order
import com.soyhenry.data.remote.dto.OrderRequestDto

interface OrderRepository {
    suspend fun createOrder(order: OrderRequestDto)
    suspend fun getOrders(): List<Order>
}
