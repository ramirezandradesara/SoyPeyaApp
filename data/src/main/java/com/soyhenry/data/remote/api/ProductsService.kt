package com.soyhenry.data.remote.api

import com.soyhenry.core.model.entities.ProductEntity
import com.soyhenry.data.remote.dto.ProductDto
import retrofit2.http.*

interface ProductsService {
    @GET("foods")
    suspend fun getAllProducts(): List<ProductDto>

    @POST("foods")
    suspend fun addProduct(@Body product: ProductEntity): ProductDto

    @DELETE("foods/{id}")
    suspend fun deleteProduct(@Path("id") id: String)
}