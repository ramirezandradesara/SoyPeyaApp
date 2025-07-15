package com.soyhenry.feature.profile.domain.usecase

import com.soyhenry.core.domain.User
import com.soyhenry.data.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String): User {
        return userRepository.getProfileByEmail(email)
    }
}
