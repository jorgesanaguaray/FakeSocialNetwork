package com.jorgesanaguaray.fakesocialnetwork.login.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.login.data.LoginRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.login.domain.LoginRepository
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
object LoginModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao): LoginRepository {
        return LoginRepositoryImpl(userDao)
    }

}