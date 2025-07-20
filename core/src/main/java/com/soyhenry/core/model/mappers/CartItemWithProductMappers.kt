package com.soyhenry.core.model.mappers

import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.entities.CartItemWithProductEntity

fun CartItemWithProductEntity.toDomain(): CartItem {
    return CartItem(
        id = cartItem.id,
        productId = cartItem.productId,
        quantity = cartItem.quantity,
        product = product.toDomain()
    )
}
