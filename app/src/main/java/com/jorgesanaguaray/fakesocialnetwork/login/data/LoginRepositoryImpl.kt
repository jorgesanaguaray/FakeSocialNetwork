package com.jorgesanaguaray.fakesocialnetwork.login.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.login.domain.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class LoginRepositoryImpl(private val userDao: UserDao) : LoginRepository {

    override suspend fun isLoginSuccessful(username: String, password: String): Boolean {

        var userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {

            userEntity = userDao.getUserByUsernameAndPassword(username, password)

        }

        if (userEntity != null) return true
        return false

    }

}