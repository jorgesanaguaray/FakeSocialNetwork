package com.jorgesanaguaray.fakesocialnetwork.authentication.data.di

import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.GetUserByUsernameAndPasswordUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.InsertUserUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsLoginSuccessfulUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsUsernameAvailableUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
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
    fun provideInsertUserUseCase(repository: UserRepository): InsertUserUseCase {
        return InsertUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsUsernameAvailableUseCase(repository: UserRepository): IsUsernameAvailableUseCase {
        return IsUsernameAvailableUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsLoginSuccessfulUseCase(repository: UserRepository): IsLoginSuccessfulUseCase {
        return IsLoginSuccessfulUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserByUsernameAndPasswordUseCase(repository: UserRepository): GetUserByUsernameAndPasswordUseCase {
        return GetUserByUsernameAndPasswordUseCase(repository)
    }

}