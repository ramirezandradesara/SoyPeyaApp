package com.soyhenry.core.mappers.product

import com.soyhenry.core.domain.Product
import com.soyhenry.core.entities.ProductEntity

fun ProductEntity.toDomain(): Product {
    return Product(id, productName, "", imageURL, price, category)
}
