package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.dto.ProductDto

interface ProductRemoteDataSource {
    suspend fun getAllProducts(): List<ProductDto>
}