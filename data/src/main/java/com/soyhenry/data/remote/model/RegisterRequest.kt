package com.soyhenry.data.remote.model

data class RegisterRequest(
    val email: String,
    val fullName: String,
    val encryptedPassword: String
)