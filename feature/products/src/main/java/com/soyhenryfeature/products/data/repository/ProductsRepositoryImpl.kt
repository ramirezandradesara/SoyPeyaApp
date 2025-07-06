package com.soyhenryfeature.products.data.repository

import com.soyhenry.core.model.database.entities.ProductEntity
import com.soyhenry.data.remote.api.ProductsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val productsService: ProductsService
) : ProductsRepository {

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productsService.getAllProducts()
    }

    override suspend fun addProduct(product: ProductEntity) {
        productsService.addProduct(product)
    }

    override suspend fun deleteProduct(productId: String) {
        productsService.deleteProduct(productId)
    }

    override suspend fun refreshProducts() {
        // Opcional: Si necesitas sincronizar con una caché local
    }
}