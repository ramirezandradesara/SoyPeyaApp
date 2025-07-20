package com.soyhenry.core2.model.domain

data class CartItem(
    val id: Int,
    val productId: String,
    val quantity: Int,
    val product: Product
)