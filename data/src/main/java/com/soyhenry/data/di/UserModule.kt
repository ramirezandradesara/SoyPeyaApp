package com.soyhenry.data.di

import com.soyhenry.core.session.UserPreferences
import com.soyhenry.data.remote.api.UserService
import com.soyhenry.data.remote.datasource.UserRemoteDataSource
import com.soyhenry.data.remote.datasource.UserRemoteDataSourceImpl
import com.soyhenry.data.repository.UserRepository
import com.soyhenry.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(
        userService: UserService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        userPreferences: UserPreferences
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, userPreferences)
    }
}