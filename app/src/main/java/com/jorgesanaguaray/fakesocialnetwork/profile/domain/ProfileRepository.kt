package com.jorgesanaguaray.fakesocialnetwork.profile.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jorge Sanaguaray
 */

interface ProfileRepository {

    suspend fun getUserByUsername(username: String): User?

    fun getPosts(): Flow<List<Post>>

    suspend fun getUserById(id: Int): User

}