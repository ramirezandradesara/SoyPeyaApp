package com.soyhenry.data.repository

import com.soyhenry.core.domain.User
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.remote.datasource.UserRemoteDataSource
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.RegisterRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource
) : UserRepository {
    override suspend fun getProfileByEmail(email: String): User {
        return remote.getProfileByEmail(email).toDomain()
    }

    override suspend fun registerUser(user: RegisterRequest) {
        remote.registerUser(user)
    }

    override suspend fun loginUser(user: LoginRequest) {
        remote.loginUser(user)
    }
}