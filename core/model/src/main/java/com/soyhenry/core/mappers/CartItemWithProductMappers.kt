package com.soyhenry.core.mappers

import com.soyhenry.core.domain.CartItem
import com.soyhenry.core.entities.CartItemWithProductEntity

fun CartItemWithProductEntity.toDomain(): CartItem {
    return CartItem(
        id = cartItem.id,
        productId = cartItem.productId,
        quantity = cartItem.quantity,
        product = product.toDomain()
    )
}
