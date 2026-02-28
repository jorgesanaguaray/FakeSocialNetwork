package com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetUserByUsernameAndPasswordUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(username: String, password: String): Result<User> {
        return repository.getUserByUsernameAndPassword(username, password)
    }

}