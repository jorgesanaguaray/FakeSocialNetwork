package com.jorgesanaguaray.fakesocialnetwork.profile.domain

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserWithPosts

/**
 * Created by Jorge Sanaguaray
 */

interface ProfileRepository {

    suspend fun getUserWithPostsByUsername(username: String): UserWithPosts?

}