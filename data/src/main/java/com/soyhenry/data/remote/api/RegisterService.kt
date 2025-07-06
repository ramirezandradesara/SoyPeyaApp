package com.soyhenry.data.remote.api

import com.soyhenry.data.remote.model.RegisterRequest
import retrofit2.http.*

interface RegisterService {
    @POST("users/register")
    suspend fun registerUser(@Body user: RegisterRequest)
}