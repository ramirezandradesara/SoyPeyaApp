package com.soyhenry.data.remote.model

import com.soyhenry.core.domain.User

data class LoginResult (
    val message: String,
    val user: User
)
