package com.soyhenry.data.local.dao

import androidx.room.*
import com.soyhenry.core.entities.OrderWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderHistoryDao {

    @Transaction
    @Query("SELECT * FROM orders")
    fun getAllOrderHistories(): Flow<List<OrderWithItems>>

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderWithItems(orderId: Long): OrderWithItems?
}
