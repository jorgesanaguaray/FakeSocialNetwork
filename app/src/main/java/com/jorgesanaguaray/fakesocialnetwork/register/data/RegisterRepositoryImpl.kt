package com.jorgesanaguaray.fakesocialnetwork.register.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.register.domain.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class RegisterRepositoryImpl(private val userDao: UserDao) : RegisterRepository {

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

}