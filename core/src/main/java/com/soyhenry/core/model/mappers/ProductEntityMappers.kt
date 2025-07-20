package com.soyhenry.core.model.mappers

import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.entities.ProductEntity

fun ProductEntity.toDomain(): Product {
    return Product(id, productName, "", imageURL, price, category)
}
