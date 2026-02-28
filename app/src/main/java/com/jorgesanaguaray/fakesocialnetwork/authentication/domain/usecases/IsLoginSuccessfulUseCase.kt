package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class IsLoginSuccessfulUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(username: String, password: String): Boolean {
        return repository.isLoginSuccessful(username, password)
    }

}