package com.soyhenry.feature.register.di

import com.soyhenry.data.remote.api.RegisterService
import com.soyhenry.feature.register.data.repository.RegisterRepository
import com.soyhenry.feature.register.data.repository.RegisterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Provides
    fun provideRegisterService(retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    @Provides
    fun provideRegisterRepository(registerService: RegisterService): RegisterRepository {
        return RegisterRepositoryImpl(registerService)
    }
}