package com.soyhenry.feature.profile.domain.usecase

import com.soyhenry.core.model.domain.User
import com.soyhenry.data.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): User? = repository.getUser()
}
