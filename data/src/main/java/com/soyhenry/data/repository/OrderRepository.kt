package com.soyhenry.data.repository

import com.soyhenry.core.domain.Order
import com.soyhenry.data.remote.dto.OrderDto

interface OrderRepository {
    suspend fun createOrder(order: OrderDto)
    suspend fun getOrders(refreshData: Boolean): List<Order>
}
