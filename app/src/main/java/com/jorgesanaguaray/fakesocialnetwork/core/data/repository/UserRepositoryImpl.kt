package com.jorgesanaguaray.fakesocialnetwork.core.data.repository

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.daos.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {

    private lateinit var searchedUsers: MutableList<User>

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toDatabase())
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toDatabase())
    }

    override suspend fun getUsers(): Result<List<User>> {

        return try {
            Result.success(userDao.getUsers().shuffled().map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override fun observeUserById(id: Int): Flow<User> {
        return userDao.observeUserById(id).map {
            it.toDomain()
        }
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id).toDomain()
    }

    override suspend fun getUserByUsername(username: String): Boolean {

        var userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {
            userEntity = userDao.getUserByUsername(username)
        }

        if (userEntity == null) return true
        return false

    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): Result<User> {

        return try {
            Result.success(userDao.getUserByUsernameAndPassword(username, password)!!.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun isLoginSuccessful(username: String, password: String): Boolean {

        var userEntity: UserEntity?

        runBlocking(Dispatchers.IO) {
            userEntity = userDao.getUserByUsernameAndPassword(username, password)
        }

        if (userEntity != null) return true
        return false

    }

    override suspend fun getSearchedUsers(query: String): Result<List<User>> {

        return try {

            val users = userDao.getUsers().map { it.toDomain() }
            searchedUsers = ArrayList()

            for (user in users) {
                if (user.username.lowercase().contains(query.lowercase())) searchedUsers.add(user)
            }

            Result.success(searchedUsers.shuffled())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}