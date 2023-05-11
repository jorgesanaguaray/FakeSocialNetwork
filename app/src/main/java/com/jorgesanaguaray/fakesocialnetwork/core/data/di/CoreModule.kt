package com.jorgesanaguaray.fakesocialnetwork.core.data.di

import android.app.Application
import androidx.room.Room
import com.jorgesanaguaray.fakesocialnetwork.Constants.Companion.DATABASE_NAME
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDatabase
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
object CoreModule {

    @Singleton
    @Provides
    fun provideUserDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao
    }

    @Singleton
    @Provides
    fun providePostDao(userDatabase: UserDatabase): PostDao {
        return userDatabase.postDao
    }

}