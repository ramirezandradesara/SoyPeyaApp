package com.soyhenry.core.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val productName: String,
    val price: Double = 0.0,
    val imageURL: String,
    val category: String = "General",
    val isFeatured: Boolean = false
)
