package com.soyhenry.core.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soyhenry.core.model.database.dao.*
import com.soyhenry.core.model.database.entities.*

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderHistoryDao(): OrderHistoryDao

    abstract fun orderItemDao(): OrderItemDao
}