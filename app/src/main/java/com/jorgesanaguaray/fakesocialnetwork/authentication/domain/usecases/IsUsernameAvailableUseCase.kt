package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.repository.AuthenticationRepository

class IsUsernameAvailableUseCase(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(username: String): Boolean {
        return repository.isUsernameAvailable(username)
    }

}