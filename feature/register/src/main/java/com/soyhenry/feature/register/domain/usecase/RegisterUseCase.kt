package com.soyhenry.feature.register.domain.usecase

import com.soyhenry.core.model.domain.User
import com.soyhenry.core.model.domain.RegisterRequest
import com.soyhenry.data.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
    ): User {
        val user = RegisterRequest(
            email = email,
            fullName = name,
            encryptedPassword = password
        )
        return userRepository.registerUser(user)
    }
}