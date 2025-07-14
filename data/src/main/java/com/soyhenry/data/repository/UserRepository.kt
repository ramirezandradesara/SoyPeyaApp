package com.soyhenry.data.repository

import com.soyhenry.core.domain.User
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.RegisterRequest

interface UserRepository {
    suspend fun getProfileByEmail(email: String): User
    suspend fun registerUser(user: RegisterRequest)
    suspend fun loginUser(user: LoginRequest)
}