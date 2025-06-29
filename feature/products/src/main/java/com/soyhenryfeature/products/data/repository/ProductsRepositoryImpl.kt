package com.soyhenryfeature.products.data.repository

import com.soyhenry.core.model.database.dao.ProductDao
import com.soyhenry.core.model.database.entities.ProductEntity
import com.soyhenry.core.repository.ProductDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val productDataSource: ProductDataSource
) : ProductsRepository {

    override suspend fun getProducts(): List<ProductEntity> {
        return productDao.getAllProducts().first()
    }

    override suspend fun addProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    override suspend fun deleteProduct(productId: String) {
        productDao.deleteProduct(productId)
    }

    override suspend fun refreshProducts() {
        val entities = productDataSource.getAllProducts()
        productDao.insertProducts(entities)
    }
}