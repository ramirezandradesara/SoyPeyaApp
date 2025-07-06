package com.soyhenry.feature.login.data.repository

import com.soyhenry.data.remote.api.LoginService
import com.soyhenry.data.remote.model.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    // private val userDao: UserDao
) : LoginRepository {
    override suspend fun loginUser(user: LoginRequest) {
        loginService.loginUser(user)
    }
}