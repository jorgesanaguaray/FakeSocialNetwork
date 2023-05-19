package com.jorgesanaguaray.fakesocialnetwork.profile.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.profile.data.ProfileRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository
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
object ProfileModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao, postDao: PostDao): ProfileRepository {
        return ProfileRepositoryImpl(userDao, postDao)
    }

}