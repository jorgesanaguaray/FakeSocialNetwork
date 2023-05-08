package com.jorgesanaguaray.fakesocialnetwork.register.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

interface RegisterRepository {

    suspend fun insertUser(user: User)

    suspend fun isUsernameAvailable(username: String): Boolean

}