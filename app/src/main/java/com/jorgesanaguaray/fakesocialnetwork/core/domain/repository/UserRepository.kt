package com.jorgesanaguaray.fakesocialnetwork.core.domain.repository

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun getUsers(): List<User>
    fun observeUserById(id: Int): Flow<User>
    suspend fun getUserById(id: Int): User
    suspend fun getUserByUsername(username: String): User?
    suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<User>
    suspend fun isLoginSuccessful(username: String, password: String): Boolean
    fun saveLoginInfo(id: Int, username: String, password: String)
    fun getCurrentUserId(): Int
    fun getCurrentUserUsername(): String
    fun logout()

}