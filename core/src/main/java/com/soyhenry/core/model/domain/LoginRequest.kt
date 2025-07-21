package com.soyhenry.core.model.domain

data class LoginRequest(
    val email: String,
    val encryptedPassword: String
)