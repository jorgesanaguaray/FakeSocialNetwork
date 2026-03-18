package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class IsUsernameAvailableUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(username: String): Boolean {

        var user: User?

        runBlocking(Dispatchers.IO) {
            user = repository.getUserByUsername(username)
        }

        return user == null

    }

}