package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.repository.AuthenticationRepository

class IsLoginSuccessfulUseCase(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(username: String, password: String): Boolean {
        return repository.isLoginSuccessful(username, password)
    }

}