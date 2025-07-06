package com.soyhenry.core.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val _id: String,
    val name: String,
    val description: String,
    val price: Double = 0.0,
    val imageUrl: String,
    val category: String = "General",
    val hasDrink: Boolean = false,
    val _v: Int = 0,
    val createdAt : String = "",
    val updatedAt: String = "",
)
