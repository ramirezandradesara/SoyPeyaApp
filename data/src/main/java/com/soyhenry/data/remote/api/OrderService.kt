package com.soyhenry.data.remote.api

import com.soyhenry.data.remote.dto.OrderDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {
    @POST("orders")
    suspend fun createOrder(@Body request: OrderDto)

    @GET("orders")
    suspend fun getOrders(): List<OrderDto>
}