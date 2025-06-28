package com.soyhenry.feature.profile.data.repository

import com.soyhenry.feature.profile.data.model.ProfileModel

interface ProfileRepository {
    suspend fun getProfile(): ProfileModel
    suspend fun updateProfile()
}