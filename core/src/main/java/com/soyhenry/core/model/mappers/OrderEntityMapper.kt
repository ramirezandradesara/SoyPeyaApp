package com.soyhenry.core.model.mappers

import com.soyhenry.core.model.domain.Order
import com.soyhenry.core.model.entities.OrderEntity

fun OrderEntity.toDomain(): Order {
    return Order(
        id = id,
        date = orderDate,
        totalAmount = totalAmount,
        totalItems = totalItems
    )
}
