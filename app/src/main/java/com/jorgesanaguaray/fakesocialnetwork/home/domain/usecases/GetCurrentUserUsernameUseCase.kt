package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetCurrentUserUsernameUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(): String {
        return repository.getCurrentUserUsername()
    }

}