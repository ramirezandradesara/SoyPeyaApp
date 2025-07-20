package com.soyhenry.feature.login.domain.usecase

import com.soyhenry.core.model.domain.LoginRequest
import com.soyhenry.core.model.domain.LoginResult
import com.soyhenry.data.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        val user = LoginRequest(
            email = email,
            encryptedPassword = password
        )
       return userRepository.loginUser(user)
    }
}