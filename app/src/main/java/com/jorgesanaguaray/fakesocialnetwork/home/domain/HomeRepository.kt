package com.jorgesanaguaray.fakesocialnetwork.home.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

interface HomeRepository {

    suspend fun getUserById(id: Int): User

    suspend fun getPosts(): Result<List<Post>>

    suspend fun getSearchedPosts(query: String): Result<List<Post>>

}