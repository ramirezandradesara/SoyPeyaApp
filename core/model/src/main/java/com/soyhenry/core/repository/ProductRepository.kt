package com.soyhenry.core.repository

import com.soyhenry.core.model.database.dao.ProductDao
import com.soyhenry.core.entities.ProductEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
/*
@Singleton
class ProductsRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productDataSource: ProductDataSource
){

    suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts().first()
    }

    suspend fun addProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    suspend fun deleteProduct(productId: String) {
        productDao.deleteProduct(productId)
    }

    suspend fun refreshProducts() {
        val entities = productDataSource.getAllProducts()
        productDao.insertProducts(entities)
    }
} */