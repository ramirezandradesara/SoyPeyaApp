package com.soyhenry.data.repository

import com.soyhenry.core.domain.User
import com.soyhenry.data.remote.dto.LoginResponseDto
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.LoginResult
import com.soyhenry.data.remote.model.RegisterRequest

interface UserRepository {
    suspend fun getProfileByEmail(email: String): User
    suspend fun registerUser(user: RegisterRequest): User
    suspend fun loginUser(user: LoginRequest): LoginResult
}