package com.soyhenry.core.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CartItemWithProductEntity(
    @Embedded val cartItem: CartItemEntity,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)
