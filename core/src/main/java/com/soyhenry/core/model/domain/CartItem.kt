package com.soyhenry.core.model.domain

data class CartItem(
    val id: Int,
    val productId: String,
    val quantity: Int,
    val product: Product
)