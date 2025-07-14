package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.api.UserService
import com.soyhenry.data.remote.dto.UserDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val service: UserService
): UserRemoteDataSource{
    override suspend fun getProfileByEmail(email: String): UserDto {
       return service.getUserByEmail(email)
    }
}