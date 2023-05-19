package com.jorgesanaguaray.fakesocialnetwork.search.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.search.data.SearchRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.search.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jorge Sanaguaray
 */

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao): SearchRepository {
        return SearchRepositoryImpl(userDao)
    }

}