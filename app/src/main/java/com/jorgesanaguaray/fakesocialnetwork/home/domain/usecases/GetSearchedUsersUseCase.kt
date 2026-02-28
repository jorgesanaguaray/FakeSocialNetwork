package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetSearchedUsersUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(query: String): Result<List<User>> {
        return repository.getSearchedUsers(query)
    }

}