package com.soyhenry.feature.login.domain.usecase

import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        val user = LoginRequest(
            email = email,
            encryptedPassword = password
        )
        userRepository.loginUser(user)
    }
}