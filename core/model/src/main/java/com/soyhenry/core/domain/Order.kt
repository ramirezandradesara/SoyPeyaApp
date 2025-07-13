package com.soyhenry.core.domain

data class Order(
    val id: String,
    val date: Long,
    val totalAmount: Double,
    val totalItems: Int,
   /// val items: List<OrderItem>
)
