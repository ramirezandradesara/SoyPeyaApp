package com.soyhenry.data.repository

import com.soyhenry.core.model.domain.LoginRequest
import com.soyhenry.core.model.domain.LoginResult
import com.soyhenry.core.model.domain.RegisterRequest
import com.soyhenry.core.model.domain.User

interface UserRepository {
    suspend fun getProfileByEmail(email: String): User
    suspend fun registerUser(user: RegisterRequest): User
    suspend fun loginUser(user: LoginRequest): LoginResult
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}