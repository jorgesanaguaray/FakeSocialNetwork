package com.jorgesanaguaray.fakesocialnetwork.home.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.home.domain.HomeRepository

/**
 * Created by Jorge Sanaguaray
 */

class HomeRepositoryImpl(private val userDao: UserDao, private val postDao: PostDao) : HomeRepository {

    private lateinit var searchedPosts: MutableList<Post>

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id).toDomain()
    }

    override suspend fun getPosts(): Result<List<Post>> {

        return try {

            Result.success(postDao.getPosts().map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun getSearchedPosts(query: String): Result<List<Post>> {

        return try {

            val posts = postDao.getPosts().map { it.toDomain() }
            searchedPosts = ArrayList()

            for (post in posts) {
                if (post.description.lowercase().contains(query.lowercase())) searchedPosts.add(post)
            }

            Result.success(searchedPosts.shuffled())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}