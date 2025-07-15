package com.soyhenry.data.repository

import com.soyhenry.core.domain.Product
import com.soyhenry.data.local.datasource.ProductLocalDataSource
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.mappers.toEntity
import com.soyhenry.core.mappers.toDomain
import com.soyhenry.data.remote.datasource.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource,
    private val local: ProductLocalDataSource
) : ProductsRepository {

    override suspend fun getAllProducts(refreshData: Boolean): List<Product> {
        return if (refreshData) {
            getRemoteThenFallbackToLocal()
        } else {
            getLocalThenFallbackToRemote()
        }
    }

    private suspend fun getRemoteThenFallbackToLocal(): List<Product> {
        return try {
            val remoteProducts = remote.getAllProducts()
            local.updateProducts(remoteProducts.map { it.toEntity() })
            remoteProducts.map { it.toDomain() }
        } catch (e: Exception) {
            val localProducts = local.getAllProducts()
            if (localProducts.isNotEmpty()) {
                localProducts.map { it.toDomain() }
            } else {
                throw e
            }
        }
    }

    private suspend fun getLocalThenFallbackToRemote(): List<Product> {
        val localProducts = local.getAllProducts()
        return if (localProducts.isNotEmpty()) {
            localProducts.map { it.toDomain() }
        } else {
            try {
                val remoteProducts = remote.getAllProducts()
                local.insertProducts(remoteProducts.map { it.toEntity() })
                remoteProducts.map { it.toDomain() }
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
