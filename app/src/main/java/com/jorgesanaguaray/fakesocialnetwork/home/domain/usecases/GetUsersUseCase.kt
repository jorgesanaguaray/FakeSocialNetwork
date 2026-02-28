package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetUsersUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(): Result<List<User>> {
        return repository.getUsers()
    }

}