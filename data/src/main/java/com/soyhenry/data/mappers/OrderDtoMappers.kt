package com.soyhenry.data.mappers

import com.soyhenry.core.domain.Order
import com.soyhenry.data.remote.dto.OrderRequestDto

fun OrderRequestDto.toDomain(): Order {
    return Order(
        id = orderId.toLongOrNull() ?: 0L, // suponiendo que viene como String
        date = timestamp,
        totalAmount = total,
        totalItems = productIds.size
    )
}
