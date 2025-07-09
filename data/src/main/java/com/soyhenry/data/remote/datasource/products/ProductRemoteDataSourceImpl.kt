package com.soyhenry.data.remote.datasource.products

import com.soyhenry.data.remote.api.ProductsService
import com.soyhenry.data.remote.dto.ProductDto
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val apiService: ProductsService
): ProductRemoteDataSource{
    override suspend fun getAllProducts(): List<ProductDto> {
       return apiService.getAllProducts()
    }
}