package com.soyhenry.core.model.mappers

import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.entities.ProductEntity

fun Product.toEntity(): ProductEntity {
    return ProductEntity(id, name, price, imgURL, category)
}
