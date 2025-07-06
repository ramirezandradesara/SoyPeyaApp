package com.soyhenry.feature.login.di

import com.soyhenry.data.remote.api.LoginService
import com.soyhenry.feature.login.data.repository.LoginRepository
import com.soyhenry.feature.login.data.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideRegisterService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    fun provideRegisterRepository(loginService: LoginService): LoginRepository {
        return LoginRepositoryImpl(loginService)
    }
}