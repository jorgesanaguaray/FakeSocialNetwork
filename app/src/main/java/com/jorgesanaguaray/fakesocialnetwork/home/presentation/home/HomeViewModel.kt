package com.jorgesanaguaray.fakesocialnetwork.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetOtherPostsUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getOtherPostsUseCase: GetOtherPostsUseCase
) : ViewModel() {

    init {

        viewModelScope.launch {
            getOtherPosts()
        }

    }

    suspend fun getOtherPosts(): List<Post> {
        return getOtherPostsUseCase()
    }

    suspend fun getUserById(id: Int): User {
        return getUserByIdUseCase(id)
    }

}