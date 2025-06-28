package com.soyhenry.feature.profile.di

import com.soyhenry.feature.profile.data.repository.ProfileRepository
import com.soyhenry.feature.profile.data.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProductsRepository(): ProfileRepository {
        return ProfileRepositoryImpl()
    }
}