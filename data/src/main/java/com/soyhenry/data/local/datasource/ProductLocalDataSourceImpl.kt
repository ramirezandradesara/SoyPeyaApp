package com.soyhenry.data.local.datasource

import com.soyhenry.core.entities.ProductEntity
import com.soyhenry.data.local.dao.ProductDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDataSource {

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts().first()
    }

    override suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertProducts(products)
    }

    override suspend fun clearProducts() {
        productDao.clearAll()
    }

    override suspend fun addProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    override suspend fun deleteProduct(productId: String) {
        productDao.deleteProduct(productId)
    }

    override suspend fun getProductById(productId: String): ProductEntity? {
        return productDao.getProductById(productId)
    }
}