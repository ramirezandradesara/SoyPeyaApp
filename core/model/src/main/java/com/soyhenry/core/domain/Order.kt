package com.soyhenry.core.domain

data class Order(
    val id: Long,
    val date: Long,
    val totalAmount: Double,
    val totalItems: Int,
   /// val items: List<OrderItem>
)
