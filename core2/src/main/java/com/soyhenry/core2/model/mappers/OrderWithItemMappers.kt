package com.soyhenry.core2.model.mappers

import com.soyhenry.core.domain.Order
import com.soyhenry.core.entities.OrderWithItems

fun OrderWithItems.toDomain(): Order {
    return Order(
        id = order.id,
        date = order.orderDate,
        totalAmount = order.totalAmount,
        totalItems = order.totalItems
        // items = items.map { it.toDomain() }
    )
}

