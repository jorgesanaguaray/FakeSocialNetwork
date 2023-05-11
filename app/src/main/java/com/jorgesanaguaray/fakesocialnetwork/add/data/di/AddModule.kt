package com.jorgesanaguaray.fakesocialnetwork.add.data.di

import com.jorgesanaguaray.fakesocialnetwork.add.data.AddRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.add.domain.AddRepository
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
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
object AddModule {

    @Singleton
    @Provides
    fun provideRepository(postDao: PostDao): AddRepository {
        return AddRepositoryImpl(postDao)
    }

}