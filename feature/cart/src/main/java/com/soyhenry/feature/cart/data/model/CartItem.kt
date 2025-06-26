package com.soyhenry.feature.cart.data.model

data class CartItem (
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imgURL: String
)