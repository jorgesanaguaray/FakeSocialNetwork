package com.jorgesanaguaray.fakesocialnetwork.authentication.data.di

import com.jorgesanaguaray.fakesocialnetwork.authentication.data.repository.AuthenticationRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.repository.AuthenticationRepository
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.GetUserByUsernameAndPasswordUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.InsertUserUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsLoginSuccessfulUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsUsernameAvailableUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(userDao: UserDao): AuthenticationRepository {
        return AuthenticationRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideInsertUserUseCase(repository: AuthenticationRepository): InsertUserUseCase {
        return InsertUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsUsernameAvailableUseCase(repository: AuthenticationRepository): IsUsernameAvailableUseCase {
        return IsUsernameAvailableUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsLoginSuccessfulUseCase(repository: AuthenticationRepository): IsLoginSuccessfulUseCase {
        return IsLoginSuccessfulUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserByUsernameAndPasswordUseCase(repository: AuthenticationRepository): GetUserByUsernameAndPasswordUseCase {
        return GetUserByUsernameAndPasswordUseCase(repository)
    }

}