package com.soyhenry.feature.register.domain.usecase

import com.soyhenry.data.remote.model.RegisterRequest
import com.soyhenry.feature.register.data.repository.RegisterRepository
import javax.inject.Inject

/* class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ) {
        val user = UserEntity(email, name, password, confirmPassword)
        registerRepository.registerUser(user)
    }
} */

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
    ) {
        val user = RegisterRequest(
            email = email,
            fullName = name,
            encryptedPassword = password
        )
        registerRepository.registerUser(user)
    }
}