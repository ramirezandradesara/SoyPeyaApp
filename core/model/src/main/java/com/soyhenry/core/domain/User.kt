package com.soyhenry.core.domain

data class User (
    val id: String,
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    val imageUrl: String = ""
)
