package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.Flow

class ObserveCurrentUserByIdUseCase(
    private val repository: UserRepository,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) {

    operator fun invoke(): Flow<User> {
        return repository.observeUserById(getCurrentUserIdUseCase())
    }

}