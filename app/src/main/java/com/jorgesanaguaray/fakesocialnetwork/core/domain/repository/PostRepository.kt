package com.jorgesanaguaray.fakesocialnetwork.core.domain.repository

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun insertPost(post: Post)
    suspend fun updatePost(post: Post)
    suspend fun getPosts(): List<Post>
    fun observePosts(): Flow<List<Post>>
    suspend fun getPostById(id: Int): Post
    suspend fun deletePostById(id: Int)

}