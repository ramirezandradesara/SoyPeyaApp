package com.soyhenry.core.mappers

import com.soyhenry.core.domain.Order
import com.soyhenry.core.entities.OrderWithItems

fun OrderWithItems.toDomain(): Order {
    return Order(
        id = order.id,
        date = order.orderDate,
        totalItems = order.totalItems,
        totalAmount = order.totalAmount,
        // items = TODO()
    )
}
