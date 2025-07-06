package com.soyhenry.core.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/* @Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val _id: String,
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    // val nationality: String = "argentine",
) */

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val _id: String,
    val email: String,
    val fullName: String,
    val encryptedPassword: String
)