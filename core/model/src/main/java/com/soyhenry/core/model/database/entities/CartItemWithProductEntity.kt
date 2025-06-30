package com.soyhenry.core.model.database.entities

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
