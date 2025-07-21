package com.soyhenry.data.local.datasource

import com.soyhenry.core.model.entities.OrderEntity
import com.soyhenry.core.model.entities.OrderItemEntity
import com.soyhenry.core.model.entities.OrderWithItems

interface OrderLocalDataSource {
    suspend fun getOrders (): List<OrderEntity>
    suspend fun getOrdersWithItems(): List<OrderWithItems>
    suspend fun updateOrders(orders: List<OrderEntity>, items: List<OrderItemEntity>)
}