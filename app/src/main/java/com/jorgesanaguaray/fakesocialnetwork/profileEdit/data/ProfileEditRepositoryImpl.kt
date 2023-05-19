package com.jorgesanaguaray.fakesocialnetwork.profileEdit.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.profileEdit.domain.ProfileEditRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class ProfileEditRepositoryImpl(private val userDao: UserDao) : ProfileEditRepository {

    override suspend fun getUserById(id: Int): Result<User> {

        return try {

            Result.success(userDao.getUserById(id).toDomain())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun isUsernameAvailable(username: String): Boolean {

        var userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {

            userEntity = userDao.getUserByUsername(username)

        }

        if (userEntity == null) return true
        return false

    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toDatabase())
    }

}