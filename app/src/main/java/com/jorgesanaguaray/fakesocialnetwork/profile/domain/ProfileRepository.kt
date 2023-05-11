package com.jorgesanaguaray.fakesocialnetwork.profile.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

interface ProfileRepository {

    suspend fun getUserByUsername(username: String): Result<User>

}