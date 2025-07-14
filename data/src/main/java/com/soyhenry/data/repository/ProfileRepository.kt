package com.soyhenry.data.repository

import com.soyhenry.core.domain.User

interface ProfileRepository {
    suspend fun getProfileByEmail(email: String): User
}