package com.soyhenryfeature.products.data.repository

import com.soyhenryfeature.products.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

// @Singleton
class ProductsRepositoryImpl @Inject constructor() : ProductsRepository {
    // In a real app, this would fetch from API/database
    private val _products = mutableListOf(
        Product(1, "Laptop", 999.99, "High performance laptop"),
        Product(2, "Smartphone", 699.99, "Latest model smartphone")
    )

    override suspend fun getProducts(): List<Product> {
        // Simulate network/database delay
        kotlinx.coroutines.delay(500)
        return _products.toList()
    }

    override suspend fun addProduct(product: Product) {
        _products.add(product)
    }

    override suspend fun deleteProduct(productId: Int) {
        _products.removeIf { it.id == productId }
    }
}