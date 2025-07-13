package com.soyhenry.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OrderRequestDto(
    // @SerializedName("_id")
    val orderId: String,
    val productIds: List<CartItemDto>,
    val total: Double,
    val timestamp: Long
)
