package com.soyhenry.data.mappers

import com.soyhenry.core.domain.User
import com.soyhenry.data.remote.dto.UserDto

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        fullName = fullName,
        password = encryptedPassword,
    )
}
