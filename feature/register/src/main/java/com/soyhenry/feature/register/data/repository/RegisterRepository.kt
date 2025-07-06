package com.soyhenry.feature.register.data.repository

import com.soyhenry.data.remote.model.RegisterRequest

interface RegisterRepository {
    suspend fun registerUser(user: RegisterRequest)
}