package com.jorgesanaguaray.fakesocialnetwork.core.data.repository

import android.content.SharedPreferences
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences
) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toDatabase())
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toDatabase())
    }

    override suspend fun getUsers(): List<User> {
        return userDao.getUsers().map {
            it.toDomain()
        }
    }

    override fun observeUserById(id: Int): Flow<User> {
        return userDao.observeUserById(id).map {
            it.toDomain()
        }
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id).toDomain()
    }

    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)?.toDomain()
    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<User> {

        return try {
            val userEntity = userDao.getUserByUsernameAndPassword(username, password)
            if (userEntity != null) {
                Result.success(userEntity.toDomain())
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun isLoginSuccessful(username: String, password: String): Boolean {

        var userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {
            userEntity = userDao.getUserByUsernameAndPassword(username, password)
        }

        return userEntity != null

    }

    override fun saveLoginInfo(id: Int, username: String, password: String) {
        sharedPreferences.edit().putInt("id", id).apply()
        sharedPreferences.edit().putString("username", username).apply()
        sharedPreferences.edit().putString("password", password).apply()
    }

    override fun getCurrentUserId(): Int {
        return sharedPreferences.getInt("id", 0)
    }

    override fun getCurrentUserUsername(): String {
        return sharedPreferences.getString("username", "") ?: ""
    }

    override fun logout() {
        sharedPreferences.edit().remove("id").apply()
        sharedPreferences.edit().remove("username").apply()
        sharedPreferences.edit().remove("password").apply()
    }

}