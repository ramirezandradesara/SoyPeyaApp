package com.soyhenryfeature.products.data.repository

import com.soyhenry.core.model.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(productId: Int)
}