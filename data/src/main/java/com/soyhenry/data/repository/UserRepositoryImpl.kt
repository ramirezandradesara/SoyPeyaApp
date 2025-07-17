package com.soyhenry.data.repository

import com.soyhenry.core.domain.User
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.remote.datasource.UserRemoteDataSource
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.LoginResult
import com.soyhenry.data.remote.model.RegisterRequest
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val userPreferences: UserPreferences
) : UserRepository {
    override suspend fun getProfileByEmail(email: String): User {
        return remote.getProfileByEmail(email).toDomain()
    }

    override suspend fun registerUser(user: RegisterRequest): User {
        return remote.registerUser(user).toDomain()
    }

    override suspend fun loginUser(user: LoginRequest): LoginResult {
        val response = remote.loginUser(user)
        return LoginResult(
            message = response.message,
            user = response.user.toDomain()
        )
    }

    override suspend fun getUser(): User? {
        return userPreferences.user.first()
    }

    override suspend fun saveUser(user: User) {
        userPreferences.saveUser(user)
    }
}