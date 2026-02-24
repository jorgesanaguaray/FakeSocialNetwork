package com.jorgesanaguaray.fakesocialnetwork.authentication.data.repository

import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.repository.AuthenticationRepository
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AuthenticationRepositoryImpl(private val userDao: UserDao): AuthenticationRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toDatabase())
    }

    override suspend fun isUsernameAvailable(username: String): Boolean {

        val userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {
            userEntity = userDao.getUserByUsername(username)
        }

        if (userEntity == null) return true
        return false

    }

    override suspend fun isLoginSuccessful(username: String, password: String): Boolean {

        var userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {
            userEntity = userDao.getUserByUsernameAndPassword(username, password)
        }

        if (userEntity != null) return true
        return false

    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<User> {

        return try {
            Result.success(userDao.getUserByUsernameAndPassword(username, password)!!.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}