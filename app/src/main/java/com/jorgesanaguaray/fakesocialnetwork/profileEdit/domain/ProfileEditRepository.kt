package com.jorgesanaguaray.fakesocialnetwork.profileEdit.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

interface ProfileEditRepository {

    suspend fun getUserById(id: Int): Result<User>

    suspend fun isUsernameAvailable(username: String): Boolean

    suspend fun updateUser(user: User)

}