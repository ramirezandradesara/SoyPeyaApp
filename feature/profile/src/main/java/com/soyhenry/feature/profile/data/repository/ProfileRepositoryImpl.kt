package com.soyhenry.feature.profile.data.repository

import com.soyhenry.feature.profile.data.model.ProfileModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    private val _profile =
        ProfileModel(
            name = "Sara",
            lastName = "Ramírez Andrade",
            email = "sara.ramirez@example.com",
            password = "Admin123",
            nationality = "argentina"
        )


    override suspend fun getProfile(): ProfileModel {
        delay(500)
        return _profile
    }

    override suspend fun updateProfile() {
        TODO("Not yet implemented")
    }
}