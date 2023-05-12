package com.jorgesanaguaray.fakesocialnetwork.postEdit.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
import com.jorgesanaguaray.fakesocialnetwork.postEdit.data.PostEditRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.postEdit.domain.PostEditRepository
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
object PostEditModule {

    @Singleton
    @Provides
    fun provideRepository(postDao: PostDao): PostEditRepository {
        return PostEditRepositoryImpl(postDao)
    }

}