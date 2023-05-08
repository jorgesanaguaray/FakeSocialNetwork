package com.jorgesanaguaray.fakesocialnetwork.login.domain

/**
 * Created by Jorge Sanaguaray
 */

interface LoginRepository {

    suspend fun isLoginSuccessful(username: String, password: String): Boolean

}