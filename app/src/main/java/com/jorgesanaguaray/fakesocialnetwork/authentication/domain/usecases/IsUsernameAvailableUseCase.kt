package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class IsUsernameAvailableUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(username: String): Boolean {
        return repository.getUserByUsername(username)
    }

}