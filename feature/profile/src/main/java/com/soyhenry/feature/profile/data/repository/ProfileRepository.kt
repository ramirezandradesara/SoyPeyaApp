package com.soyhenry.feature.profile.data.repository

import com.soyhenry.feature.profile.data.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun updateProfile()
}