package com.jorgesanaguaray.fakesocialnetwork.search.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

interface SearchRepository {

    suspend fun getUsers(): Result<List<User>>

    suspend fun getSearchedUsers(query: String): Result<List<User>>

}