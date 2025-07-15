package com.soyhenry.data.local.datasource

import androidx.room.Transaction
import com.soyhenry.core.entities.OrderEntity
import com.soyhenry.core.entities.OrderItemEntity
import com.soyhenry.core.entities.OrderWithItems
import com.soyhenry.data.local.dao.OrderDao
import com.soyhenry.data.local.dao.OrderItemDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderLocalDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao
) : OrderLocalDataSource {

    override suspend fun getOrders(): List<OrderEntity> {
        return orderDao.getOrders()
    }

    override suspend fun getOrdersWithItems(): List<OrderWithItems> {
        return orderDao.getOrdersWithItems()
    }

    @Transaction
    override suspend fun updateOrders(orders: List<OrderEntity>, orderItems: List<OrderItemEntity>) {
        orderDao.clearOrders()
        orderItemDao.clearOrderItems()

        orderDao.insertOrders(orders)
        orderItemDao.insertOrderItems(orderItems)
    }
}
