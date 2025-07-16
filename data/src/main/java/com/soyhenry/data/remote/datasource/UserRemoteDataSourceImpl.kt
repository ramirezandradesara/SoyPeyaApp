package com.soyhenry.data.remote.datasource

import com.soyhenry.data.remote.api.UserService
import com.soyhenry.data.remote.dto.LoginResponseDto
import com.soyhenry.data.remote.dto.UserDto
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.RegisterRequest
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val service: UserService
): UserRemoteDataSource{
    override suspend fun getProfileByEmail(email: String): UserDto {
       return service.getUserByEmail(email)
    }

    override suspend fun registerUser(user: RegisterRequest) {
        service.registerUser(user)
    }
    override suspend fun loginUser(user: LoginRequest): LoginResponseDto {
        return service.loginUser(user)
    }
}