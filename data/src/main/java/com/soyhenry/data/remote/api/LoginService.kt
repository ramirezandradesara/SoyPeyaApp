package com.soyhenry.data.remote.api

import com.soyhenry.data.remote.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest)
}