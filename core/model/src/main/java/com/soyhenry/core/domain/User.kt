package com.soyhenry.core.domain

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val password: String,
    val image: String = ""
)
