package com.soyhenry.core.model.domain

data class RegisterRequest(
    val email: String,
    val fullName: String,
    val encryptedPassword: String
)