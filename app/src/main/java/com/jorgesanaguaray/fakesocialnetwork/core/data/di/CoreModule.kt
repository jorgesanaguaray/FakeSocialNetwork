package com.jorgesanaguaray.fakesocialnetwork.core.data.di

import android.app.Application
import androidx.room.Room
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.database.FakeSocialNetworkDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.repository.PostRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.core.data.repository.UserRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
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

    @Provides
    @Singleton
    fun provideDatabase(application: Application): FakeSocialNetworkDatabase {
        return Room.databaseBuilder(application, FakeSocialNetworkDatabase::class.java, "fake_social_network").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: FakeSocialNetworkDatabase): UserDao {
        return database.userDao
    }

    @Provides
    @Singleton
    fun providePostDao(database: FakeSocialNetworkDatabase): PostDao {
        return database.postDao
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun providePostRepository(postDao: PostDao): PostRepository {
        return PostRepositoryImpl(postDao)
    }

}