package com.soyhenry.data.repository

import com.soyhenry.core.domain.Product

interface ProductsRepository {
    suspend fun getAllProducts(refreshData: Boolean): List<Product>
    //suspend fun addProduct(product: ProductEntity)
    //suspend fun deleteProduct(productId: String)
    //suspend fun refreshProducts()
}
