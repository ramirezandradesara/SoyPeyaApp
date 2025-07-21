package com.soyhenry.data.mappers

import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.entities.ProductEntity
import com.soyhenry.data.remote.dto.ProductDto

fun ProductDto.toDomain(): Product {
    return Product(id, name, description, imageUrl, price, category)
}

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(id, name, price, imageUrl, category)
}