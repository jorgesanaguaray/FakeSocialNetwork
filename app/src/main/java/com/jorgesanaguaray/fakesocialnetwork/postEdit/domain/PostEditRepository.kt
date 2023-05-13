package com.jorgesanaguaray.fakesocialnetwork.postEdit.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post

/**
 * Created by Jorge Sanaguaray
 */

interface PostEditRepository {

    suspend fun getPostById(id: Int): Result<Post>

    suspend fun updatePost(post: Post)

    suspend fun deletePostById(id: Int)

}