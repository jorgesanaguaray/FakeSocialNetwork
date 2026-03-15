package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase

class GetCurrentUserByIdUseCase(
    private val repository: UserRepository,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) {

    suspend operator fun invoke(): User {
        return repository.getUserById(getCurrentUserIdUseCase())
    }

}