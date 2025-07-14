package com.soyhenry.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id")
    val id: String,
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    val createdAt: String,
    val updatedAt: String,
)
