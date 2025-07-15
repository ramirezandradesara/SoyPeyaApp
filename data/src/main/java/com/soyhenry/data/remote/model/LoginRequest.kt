package com.soyhenry.data.remote.model

data class LoginRequest(
    val email: String,
    val encryptedPassword: String
)