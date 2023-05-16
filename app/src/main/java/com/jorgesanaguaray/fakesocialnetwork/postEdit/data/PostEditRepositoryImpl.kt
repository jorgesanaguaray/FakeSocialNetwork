package com.jorgesanaguaray.fakesocialnetwork.postEdit.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.postEdit.domain.PostEditRepository

/**
 * Created by Jorge Sanaguaray
 */

class PostEditRepositoryImpl(private val postDao: PostDao) : PostEditRepository {

    override suspend fun getPostById(id: Int): Result<Post> {

        return try {

            Result.success(postDao.getPostById(id).toDomain())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun updatePost(post: Post) {

        postDao.updatePost(post.toDatabase())

    }

    override suspend fun deletePostById(id: Int) {

        postDao.deletePostById(id)

    }

}