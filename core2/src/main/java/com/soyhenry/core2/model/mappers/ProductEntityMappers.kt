package com.soyhenry.core2.model.mappers

import com.soyhenry.core.Product
import com.soyhenry.core.ProductEntity

fun ProductEntity.toDomain(): Product {
    return Product(id, productName, "", imageURL, price, category)
}
