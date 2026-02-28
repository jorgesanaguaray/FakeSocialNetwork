package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ObserveUserByIdUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(id: Int): Flow<User> {
        return repository.observeUserById(id)
    }

}