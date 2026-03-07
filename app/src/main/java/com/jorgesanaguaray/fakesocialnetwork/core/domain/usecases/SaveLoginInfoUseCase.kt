package com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class SaveLoginInfoUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(id: Int, username: String, password: String) {
        repository.saveLoginInfo(id, username, password)
    }

}