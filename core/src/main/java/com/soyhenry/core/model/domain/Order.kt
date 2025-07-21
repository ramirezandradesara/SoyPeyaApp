package com.soyhenry.core.model.domain

data class Order (
    val id: String,
    val date: Long,
    val totalAmount: Double,
    val totalItems: Int,
)
