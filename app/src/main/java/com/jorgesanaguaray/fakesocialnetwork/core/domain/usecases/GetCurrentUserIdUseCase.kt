package com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetCurrentUserIdUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(): Int {
        return repository.getCurrentUserId()
    }

}