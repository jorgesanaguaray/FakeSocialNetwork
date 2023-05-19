package com.jorgesanaguaray.fakesocialnetwork.profileEdit.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.profileEdit.data.ProfileEditRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.profileEdit.domain.ProfileEditRepository
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
object ProfileEditModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao): ProfileEditRepository {
        return ProfileEditRepositoryImpl(userDao)
    }

}