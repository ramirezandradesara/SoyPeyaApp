package com.soyhenry.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soyhenry.core.model.entities.*
import com.soyhenry.data.local.dao.*

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderHistoryDao(): OrderDao

    abstract fun orderItemDao(): OrderItemDao
}