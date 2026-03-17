package com.jorgesanaguaray.fakesocialnetwork.core.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.database.FakeSocialNetworkDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.repository.PostRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.core.data.repository.UserRepositoryImpl
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.SaveLoginInfoUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetCurrentUserUsernameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("user_login_info", Context.MODE_PRIVATE)
    }

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
    fun provideUserRepository(userDao: UserDao, sharedPreferences: SharedPreferences): UserRepository {
        return UserRepositoryImpl(userDao, sharedPreferences)
    }

    @Provides
    @Singleton
    fun providePostRepository(postDao: PostDao): PostRepository {
        return PostRepositoryImpl(postDao)
    }

    @Provides
    @Singleton
    fun provideSaveLoginInfoUseCase(repository: UserRepository): SaveLoginInfoUseCase {
        return SaveLoginInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserIdUseCase(repository: UserRepository): GetCurrentUserIdUseCase {
        return GetCurrentUserIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUsernameUseCase(repository: UserRepository): GetCurrentUserUsernameUseCase {
        return GetCurrentUserUsernameUseCase(repository)
    }

}