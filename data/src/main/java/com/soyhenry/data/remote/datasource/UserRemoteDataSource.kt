package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.dto.LoginResponseDto
import com.soyhenry.data.remote.dto.UserDto
import com.soyhenry.core.model.domain.LoginRequest
import com.soyhenry.core.model.domain.RegisterRequest

interface UserRemoteDataSource {
    suspend fun getProfileByEmail(email: String): UserDto
    suspend fun registerUser(user: RegisterRequest): UserDto
    suspend fun loginUser(user: LoginRequest): LoginResponseDto
}