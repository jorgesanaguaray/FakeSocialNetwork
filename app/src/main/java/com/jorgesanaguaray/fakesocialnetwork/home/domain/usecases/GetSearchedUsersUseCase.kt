package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository

class GetSearchedUsersUseCase(
    private val repository: UserRepository
) {

    private lateinit var searchedUsers: MutableList<User>

    suspend operator fun invoke(query: String): List<User> {

        val users = repository.getUsers()

        searchedUsers = ArrayList()

        for (user in users) {
            if (user.username.lowercase().contains(query.lowercase())) searchedUsers.add(user)
        }

        return searchedUsers.shuffled()

    }

}