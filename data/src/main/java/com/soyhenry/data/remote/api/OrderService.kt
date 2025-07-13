package com.soyhenry.data.remote.api

import com.soyhenry.data.remote.dto.OrderRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {
    @POST("orders")
    suspend fun createOrder(@Body request: OrderRequestDto)

    @GET("orders")
    suspend fun getOrders(): List<OrderRequestDto>
}