package com.soyhenryfeature.products.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imgURL: String
)