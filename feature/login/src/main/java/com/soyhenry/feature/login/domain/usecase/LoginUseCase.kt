package com.soyhenry.feature.login.domain.usecase

import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.feature.login.data.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        val user = LoginRequest(
            email = email,
            encryptedPassword = password
        )
        loginRepository.loginUser(user)
    }
}