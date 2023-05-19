package com.jorgesanaguaray.fakesocialnetwork.register.data.di

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.register.data.RegisterRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.register.domain.RegisterRepository
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
object RegisterModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao): RegisterRepository {
        return RegisterRepositoryImpl(userDao)
    }

}