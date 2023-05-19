package com.jorgesanaguaray.fakesocialnetwork.home.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Jorge Sanaguaray
 */

class HomeRepositoryImpl(private val userDao: UserDao, private val postDao: PostDao) : HomeRepository {

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