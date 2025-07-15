package com.soyhenry.data.local.datasource

import com.soyhenry.core.entities.OrderEntity
import com.soyhenry.core.entities.OrderItemEntity
import com.soyhenry.core.entities.OrderWithItems

interface OrderLocalDataSource {
    suspend fun getOrders (): List<OrderEntity>
    suspend fun getOrdersWithItems(): List<OrderWithItems>
    suspend fun updateOrders(orders: List<OrderEntity>, items: List<OrderItemEntity>)
}