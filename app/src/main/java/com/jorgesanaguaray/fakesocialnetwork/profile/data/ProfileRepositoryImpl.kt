package com.jorgesanaguaray.fakesocialnetwork.profile.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserWithPosts
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository

/**
 * Created by Jorge Sanaguaray
 */

class ProfileRepositoryImpl(private val userDao: UserDao) : ProfileRepository {

    override suspend fun getUserWithPostsByUsername(username: String): UserWithPosts? {
        return userDao.getUserWithPostsByUsername(username)
    }

}