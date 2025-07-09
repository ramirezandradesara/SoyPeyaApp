package com.soyhenry.data.local.datasource.products

import com.soyhenry.core.entities.ProductEntity

interface ProductLocalDataSource {
    suspend fun getAllProducts(): List<ProductEntity>
    suspend fun insertProducts(products: List<ProductEntity>)
    suspend fun clearProducts()
    suspend fun addProduct(product: ProductEntity)
    suspend fun deleteProduct(productId: String)
    suspend fun getProductById(productId: String): ProductEntity?
}
