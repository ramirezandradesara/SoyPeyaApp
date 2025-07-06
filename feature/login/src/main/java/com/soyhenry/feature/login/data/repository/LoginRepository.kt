package com.soyhenry.feature.login.data.repository

import com.soyhenry.data.remote.model.LoginRequest

interface LoginRepository {
    suspend fun loginUser(user: LoginRequest)
}