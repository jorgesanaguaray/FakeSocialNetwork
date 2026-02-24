package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.repository.AuthenticationRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

class GetUserByUsernameAndPasswordUseCase(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(username: String, password: String): Result<User> {
        return repository.getUserByUsernameAndPassword(username, password)
    }

}