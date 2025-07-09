package com.soyhenry.data.local.datasource.products

import com.soyhenry.core.model.database.dao.ProductDao
import com.soyhenry.core.entities.ProductEntity
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
}
