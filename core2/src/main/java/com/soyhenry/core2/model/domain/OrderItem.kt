package com.soyhenry.core2.model.domain

data class OrderItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)
