package com.soyhenry.data.mappers

import com.soyhenry.core.model.domain.Order
import com.soyhenry.core.model.entities.OrderEntity
import com.soyhenry.core.model.entities.OrderItemEntity
import com.soyhenry.data.remote.dto.OrderDto

fun OrderDto.toDomain(): Order {
    return Order(
        id = orderId,
        date = timestamp,
        totalAmount = total,
        totalItems = productIds.sumOf { it.quantity }
    )
}

fun OrderDto.toOrderEntity(): OrderEntity {
    return OrderEntity(
        id = orderId,
        orderDate = timestamp,
        totalAmount = total,
        totalItems = productIds.sumOf { it.quantity }
    )
}

fun OrderDto.toOrderItemEntities(): List<OrderItemEntity> {
    return productIds.map { cartItem ->
        OrderItemEntity(
            orderItemId = "$orderId-${cartItem.productId}",
            orderId = orderId,
            productId = cartItem.productId,
            quantity = cartItem.quantity,
            price = cartItem.price,
        )
    }
}