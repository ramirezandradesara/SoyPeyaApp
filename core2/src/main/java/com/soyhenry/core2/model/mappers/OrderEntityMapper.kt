package com.soyhenry.core2.model.mappers

import com.soyhenry.core.domain.Order
import com.soyhenry.core.entities.OrderEntity

fun OrderEntity.toDomain(): Order {
    return Order(
        id = id,
        date = orderDate,
        totalAmount = totalAmount,
        totalItems = totalItems
        // agregar items, relación con OrderItemEntity
    )
}
