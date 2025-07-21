package com.soyhenry.data.local.dao

import androidx.room.*
import com.soyhenry.core.model.entities.OrderEntity
import com.soyhenry.core.model.entities.OrderWithItems

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Transaction
    @Query("SELECT * FROM orders")
    suspend fun getOrders(): List<OrderEntity>

    @Transaction
    @Query("SELECT * FROM orders")
    suspend fun getOrdersWithItems(): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderWithItemsById(orderId: String): OrderWithItems?

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("DELETE FROM orders")
    suspend fun clearOrders()
}
