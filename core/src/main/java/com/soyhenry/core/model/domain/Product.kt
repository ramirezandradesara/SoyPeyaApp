package com.soyhenry.core.model.domain

data class Product (
    val id: String,
    val name: String,
    val description: String,
    val imgURL: String,
    val price: Double,
    val category: String,
)