package com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class LogoutUseCase(
    private val repository: UserRepository
) {

    operator fun invoke() {
        repository.logout()
    }

}