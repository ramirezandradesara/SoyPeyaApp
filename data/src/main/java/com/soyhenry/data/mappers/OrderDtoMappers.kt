package com.soyhenry.data.mappers

import com.soyhenry.core.domain.Order
import com.soyhenry.core.entities.OrderEntity
import com.soyhenry.core.entities.OrderItemEntity
import com.soyhenry.data.remote.dto.OrderDto

fun OrderDto.toDomain(): Order {
    return Order(
        id = orderId,
        date = timestamp,
        totalAmount = total,
        totalItems = productIds.size
    )
}

fun OrderDto.toOrderEntity(): OrderEntity {
    val totalItems = productIds.sumOf { it.quantity }
    return OrderEntity(
        id = orderId,
        orderDate = timestamp,
        totalAmount = total,
        totalItems = totalItems
    )
}

fun OrderDto.toOrderItemEntities(): List<OrderItemEntity> {
    return productIds.map { cartItem ->
        OrderItemEntity(
            orderItemId = "$orderId-${cartItem.productId}", // o UUID.randomUUID().toString()
            orderId = orderId,
            productId = cartItem.productId,
            quantity = cartItem.quantity,
            price = cartItem.price,
        )
    }
}