package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.dto.OrderDto

interface OrderRemoteDataSource {
    suspend fun getOrders(): List<OrderDto>
    suspend fun createOrder(order: OrderDto)
}