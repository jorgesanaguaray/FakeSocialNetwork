package com.jorgesanaguaray.fakesocialnetwork.home.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.DeletePostByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetCurrentUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetOtherPostsUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetPostByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetSearchedUsersUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUsersUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.InsertPostUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveCurrentUserPostsUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveCurrentUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.UpdatePostUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.UpdateUserUseCase
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
    fun provideGetUserByIdUseCase(repository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObserveCurrentUserByIdUseCase(repository: UserRepository, useCase: GetCurrentUserIdUseCase): ObserveCurrentUserByIdUseCase {
        return ObserveCurrentUserByIdUseCase(repository, useCase)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(repository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(repository)
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
    fun provideInsertPostUseCase(repository: PostRepository): InsertPostUseCase {
        return InsertPostUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPostByIdUseCase(repository: PostRepository): GetPostByIdUseCase {
        return GetPostByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdatePostUseCase(repository: PostRepository): UpdatePostUseCase {
        return UpdatePostUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeletePostByIdUseCase(repository: PostRepository): DeletePostByIdUseCase {
        return DeletePostByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOtherPostsUseCase(repository: PostRepository, useCase: GetCurrentUserIdUseCase): GetOtherPostsUseCase {
        return GetOtherPostsUseCase(repository, useCase)
    }

}