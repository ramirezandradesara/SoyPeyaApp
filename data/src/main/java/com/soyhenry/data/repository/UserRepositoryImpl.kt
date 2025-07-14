package com.soyhenry.data.repository

import com.soyhenry.core.domain.User
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.remote.datasource.UserRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource
) : UserRepository {
    override suspend fun getProfileByEmail(email: String): User {
        return remote.getProfileByEmail(email).toDomain()
    }
}