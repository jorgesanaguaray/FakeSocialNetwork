package com.jorgesanaguaray.fakesocialnetwork.core.data.repository

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(private val postDao: PostDao): PostRepository {

    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post.toDatabase())
    }

    override suspend fun updatePost(post: Post) {
        postDao.updatePost(post.toDatabase())
    }

    override suspend fun getPosts(): List<Post> {

        return postDao.getPosts().map {
            it.toDomain()
        }

    }

    override fun observePosts(): Flow<List<Post>> {

        return postDao.observePosts().map { postsEntity ->

            postsEntity.map {
                it.toDomain()
            }

        }

    }

    override suspend fun getPostById(id: Int): Result<Post> {

        return try {
            Result.success(postDao.getPostById(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun deletePostById(id: Int) {
        postDao.deletePostById(id)
    }

}