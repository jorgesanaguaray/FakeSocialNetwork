package com.jorgesanaguaray.fakesocialnetwork.profile.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Jorge Sanaguaray
 */

class ProfileRepositoryImpl(private val userDao: UserDao, private val postDao: PostDao) : ProfileRepository {

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id).toDomain()
    }

    override fun getPosts(): Flow<List<Post>> {

        return postDao.getPosts().map { postsEntity ->

            postsEntity.map {

                it.toDomain()

            }

        }

    }

}