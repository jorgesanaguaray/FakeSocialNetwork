package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetUserByIdUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(id: Int): User {
        return repository.getUserById(id)
    }

}