package com.jorgesanaguaray.fakesocialnetwork.login.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

interface LoginRepository {

    suspend fun isLoginSuccessful(username: String, password: String): Boolean

    suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<User>

}