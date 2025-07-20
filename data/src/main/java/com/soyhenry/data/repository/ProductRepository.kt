package com.soyhenry.data.repository

import com.soyhenry.core.model.domain.Product

interface ProductsRepository {
    suspend fun getAllProducts(refreshData: Boolean): List<Product>
}
