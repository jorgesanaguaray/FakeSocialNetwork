package com.jorgesanaguaray.fakesocialnetwork.profile.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserWithPosts
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository

/**
 * Created by Jorge Sanaguaray
 */

class ProfileRepositoryImpl(private val userDao: UserDao) : ProfileRepository {

    override suspend fun getUserByUsername(username: String): Result<User> {

        return try {

            Result.success(userDao.getUserByUsername(username)!!.toDomain())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun getUserWithPosts(userId: Int): UserWithPosts? {
        return userDao.getUserWithPosts(userId)
    }

}