package com.soyhenry.data.repository

import com.soyhenry.core.domain.Product
import com.soyhenry.data.local.datasource.products.ProductLocalDataSource
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.mappers.toEntity
import com.soyhenry.data.remote.datasource.products.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource,
    private val local: ProductLocalDataSource
) : ProductsRepository {

    override suspend fun getAllProducts(refreshData: Boolean): List<Product> {
        return if (refreshData) {
            val remoteProducts = remote.getAllProducts()
            //local.clearProducts()
            //local.insertProducts(remoteProducts.map { it.toEntity() })
            remoteProducts.map { it.toDomain() }
        } else {
            val localProducts = local.getAllProducts()
            if (localProducts.isNotEmpty()) {
               localProducts.map { it.toDomain() }
            } else {
                val remoteProducts = remote.getAllProducts()
                local.insertProducts(remoteProducts.map { it.toEntity() })
                remoteProducts.map { it.toDomain() }
            }
        }
    }
}
