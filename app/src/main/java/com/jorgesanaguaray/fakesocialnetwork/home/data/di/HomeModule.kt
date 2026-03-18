package com.jorgesanaguaray.fakesocialnetwork.home.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetCurrentUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetOtherPostsUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetSearchedUsersUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveCurrentUserPostsUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveCurrentUserByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideObserveCurrentUserByIdUseCase(repository: UserRepository, useCase: GetCurrentUserIdUseCase): ObserveCurrentUserByIdUseCase {
        return ObserveCurrentUserByIdUseCase(repository, useCase)
    }

    @Provides
    @Singleton
    fun provideGetSearchedUsersUseCase(repository: UserRepository): GetSearchedUsersUseCase {
        return GetSearchedUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserByIdUseCase(repository: UserRepository, useCase: GetCurrentUserIdUseCase): GetCurrentUserByIdUseCase {
        return GetCurrentUserByIdUseCase(repository, useCase)
    }

    @Provides
    @Singleton
    fun provideObserveCurrentUserPostsUseCase(repository: PostRepository, useCase: GetCurrentUserIdUseCase): ObserveCurrentUserPostsUseCase {
        return ObserveCurrentUserPostsUseCase(repository, useCase)
    }

    @Provides
    @Singleton
    fun provideGetOtherPostsUseCase(repository: PostRepository, useCase: GetCurrentUserIdUseCase): GetOtherPostsUseCase {
        return GetOtherPostsUseCase(repository, useCase)
    }

}