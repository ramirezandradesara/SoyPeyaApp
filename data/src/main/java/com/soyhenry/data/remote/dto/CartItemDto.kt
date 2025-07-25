package com.soyhenry.data.remote.dto

data class CartItemDto (
    val productId: String = System.currentTimeMillis().toString(),
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    val quantity: Int
)
