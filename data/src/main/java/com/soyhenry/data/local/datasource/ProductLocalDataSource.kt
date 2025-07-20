package com.soyhenry.data.local.datasource

import com.soyhenry.core.model.entities.ProductEntity

interface ProductLocalDataSource {
    suspend fun getAllProducts(): List<ProductEntity>
    suspend fun insertProducts(products: List<ProductEntity>)
    suspend fun clearProducts()
    suspend fun addProduct(product: ProductEntity)
    suspend fun deleteProduct(productId: String)
    suspend fun getProductById(productId: String): ProductEntity?
    suspend fun updateProducts(products: List<ProductEntity>)
}