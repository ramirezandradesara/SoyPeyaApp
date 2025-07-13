package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.api.OrderService
import com.soyhenry.data.remote.dto.OrderDto
import javax.inject.Inject

class OrderRemoteDataSourceImpl @Inject constructor(
    private val apiService: OrderService
): OrderRemoteDataSource {
    override suspend fun getOrders(): List<OrderDto> {
       return apiService.getOrders()
    }

    override suspend fun createOrder(order: OrderDto) {
        apiService.createOrder(order)
    }
}