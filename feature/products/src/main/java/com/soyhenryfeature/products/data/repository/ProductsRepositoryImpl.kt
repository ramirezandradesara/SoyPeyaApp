package com.soyhenryfeature.products.data.repository

import com.soyhenry.core.entities.ProductEntity
import com.soyhenry.data.remote.api.ProductsService
import com.soyhenry.data.remote.dto.ProductDto
import javax.inject.Inject
import javax.inject.Singleton

/*
// combinacion de ambos data source
@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val remote: ProductsService,
    private val local:
) : ProductsRepository {

    override suspend fun getAllProducts(): List<Product> {
        return remote.getAllProducts()
    }

    override suspend fun addProduct(product: ProductEntity) {
        remote.addProduct(product)
    }

    override suspend fun deleteProduct(productId: String) {
        remote.deleteProduct(productId)
    }

    override suspend fun refreshProducts() {
        // Opcional: Si necesitas sincronizar con una caché local
    }
}*/