package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.repository

import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

interface AuthenticationRepository {

    suspend fun insertUser(user: User)
    suspend fun isUsernameAvailable(username: String): Boolean
    suspend fun isLoginSuccessful(username: String, password: String): Boolean
    suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<User>

}