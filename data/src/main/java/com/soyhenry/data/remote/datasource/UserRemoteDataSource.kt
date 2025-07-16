package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.dto.LoginResponseDto
import com.soyhenry.data.remote.dto.UserDto
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.RegisterRequest

interface UserRemoteDataSource {
    suspend fun getProfileByEmail(email: String): UserDto
    suspend fun registerUser(user: RegisterRequest)
    suspend fun loginUser(user: LoginRequest): LoginResponseDto
}