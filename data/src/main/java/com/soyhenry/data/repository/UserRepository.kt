package com.soyhenry.data.repository

import com.soyhenry.core.domain.User

interface UserRepository {
    suspend fun getProfileByEmail(email: String): User
}