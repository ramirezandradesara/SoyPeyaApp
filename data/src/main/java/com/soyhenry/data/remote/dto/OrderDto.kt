package com.soyhenry.data.remote.dto

data class OrderDto(
    val orderId: String,
    val productIds: List<CartItemDto>,
    val total: Double,
    val timestamp: Long
)
