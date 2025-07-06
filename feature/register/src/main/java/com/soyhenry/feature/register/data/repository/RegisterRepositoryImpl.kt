package com.soyhenry.feature.register.data.repository

import com.soyhenry.data.remote.api.RegisterService
import com.soyhenry.data.remote.model.RegisterRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepositoryImpl @Inject constructor(
    private val registerService: RegisterService,
    // private val userDao: UserDao // Si usas Room
) : RegisterRepository {
    override suspend fun registerUser(user: RegisterRequest) {
        registerService.registerUser(user)
    }
}