package com.soyhenry.data.mappers

import com.soyhenry.core.domain.Order
import com.soyhenry.data.remote.dto.OrderDto

fun OrderDto.toDomain(): Order {
    return Order(
        id = orderId,
        date = timestamp,
        totalAmount = total,
        totalItems = productIds.size
    )
}
