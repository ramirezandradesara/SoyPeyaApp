package com.soyhenry.core.model.mappers

import com.soyhenry.core.model.domain.Order
import com.soyhenry.core.model.entities.OrderWithItems

fun OrderWithItems.toDomain(): Order {
    return Order(
        id = order.id,
        date = order.orderDate,
        totalAmount = order.totalAmount,
        totalItems = order.totalItems
    )
}

