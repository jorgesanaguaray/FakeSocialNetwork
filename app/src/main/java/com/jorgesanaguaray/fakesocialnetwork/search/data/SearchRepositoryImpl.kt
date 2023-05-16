package com.jorgesanaguaray.fakesocialnetwork.search.data

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.search.domain.SearchRepository

/**
 * Created by Jorge Sanaguaray
 */

class SearchRepositoryImpl(private val userDao: UserDao) : SearchRepository {

    private lateinit var searchedUsers: MutableList<User>

    override suspend fun getUsers(): Result<List<User>> {

        return try {

            Result.success(userDao.getUsers().shuffled().map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

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