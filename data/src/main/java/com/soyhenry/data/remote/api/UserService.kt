package com.soyhenry.data.remote.api

import com.soyhenry.data.remote.dto.LoginResponseDto
import com.soyhenry.data.remote.dto.UserDto
import com.soyhenry.core.model.domain.LoginRequest
import com.soyhenry.core.model.domain.RegisterRequest
import retrofit2.http.*

interface UserService {
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponseDto

    @POST("users/register")
    suspend fun registerUser(@Body user: RegisterRequest): UserDto

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): UserDto
}