package com.jorgesanaguaray.fakesocialnetwork.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetOtherPostsUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getOtherPostsUseCase: GetOtherPostsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getPosts()
        }
    }

    fun getUserId(): Int {
        return getCurrentUserIdUseCase()
    }

    suspend fun getPosts(): List<Post> {
        return getOtherPostsUseCase(getCurrentUserIdUseCase())
    }

    fun getUserById(id: Int, callback: (User) -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {

            val user = getUserAsync(id)

            withContext(Dispatchers.Main) {
                callback(user)
            }

        }

    }

    private suspend fun getUserAsync(id: Int): User {

        return withContext(Dispatchers.IO) {

            val user: User = getUserByIdUseCase(id)
            user

        }

    }

}