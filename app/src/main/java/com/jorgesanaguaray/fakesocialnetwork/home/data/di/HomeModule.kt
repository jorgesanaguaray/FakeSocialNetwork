package com.jorgesanaguaray.fakesocialnetwork.home.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.home.data.HomeRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.home.domain.HomeRepository
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
object HomeModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao, postDao: PostDao): HomeRepository {
        return HomeRepositoryImpl(userDao, postDao)
    }

}