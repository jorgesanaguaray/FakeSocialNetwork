package com.jorgesanaguaray.fakesocialnetwork.home.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jorge Sanaguaray
 */

interface HomeRepository {

    suspend fun getUserById(id: Int): User

    fun getPosts(): Flow<List<Post>>

}