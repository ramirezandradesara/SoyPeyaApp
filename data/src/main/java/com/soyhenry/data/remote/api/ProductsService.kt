package com.soyhenry.data.remote.api

import com.soyhenry.core.model.database.entities.ProductEntity
import retrofit2.http.*

interface ProductsService {
    @GET("foods")
    suspend fun getAllProducts(): List<ProductEntity>

    @POST("foods")
    suspend fun addProduct(@Body product: ProductEntity): ProductEntity

    @DELETE("foods/{id}")
    suspend fun deleteProduct(@Path("id") id: String)
}