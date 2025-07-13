package com.soyhenry.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderDate: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val totalItems: Int
)


