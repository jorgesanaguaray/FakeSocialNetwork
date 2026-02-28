package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class UpdateUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(user: User) {
        repository.updateUser(user)
    }

}