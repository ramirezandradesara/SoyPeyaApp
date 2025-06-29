package com.soyhenry.core.model.database.dao

import androidx.room.*
import com.soyhenry.core.model.database.entities.OrderWithItems
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
