package com.jorgesanaguaray.fakesocialnetwork.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetOtherPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
    private val getOtherPostsUseCase: GetOtherPostsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {

        viewModelScope.launch {
            getOtherPosts()
        }

    }

    suspend fun getUserById(id: Int): User {
        return repository.getUserById(id)
    }

    private fun getOtherPosts() {

        viewModelScope.launch {

            _state.update {
                it.copy(isContainer = false, isLoading = true)
            }

            delay(3000)

            _state.update {
                it.copy(posts = getOtherPostsUseCase(), isContainer = true, isLoading = false)
            }

        }

    }

}