package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.dto.UserDto

interface UserRemoteDataSource {
    suspend fun getProfileByEmail(email: String): UserDto
}