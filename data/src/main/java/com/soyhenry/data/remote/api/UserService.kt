package com.soyhenry.data.remote.api

import com.soyhenry.data.remote.dto.UserDto
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.RegisterRequest
import retrofit2.http.*

interface UserService {
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest)

    @POST("users/register")
    suspend fun registerUser(@Body user: RegisterRequest)

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): UserDto
}