package com.soyhenry.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soyhenry.core.entities.*
import com.soyhenry.data.local.dao.*

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderHistoryDao(): OrderHistoryDao

    abstract fun orderItemDao(): OrderItemDao
}