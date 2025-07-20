package com.soyhenry.core2.model.mappers

import com.soyhenry.core.domain.Product
import com.soyhenry.core.entities.ProductEntity

fun Product.toEntity(): ProductEntity {
    return ProductEntity(id, name, price, imgURL, category)
}
