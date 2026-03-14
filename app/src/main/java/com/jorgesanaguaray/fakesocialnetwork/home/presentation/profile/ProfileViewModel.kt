package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.LogoutUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveCurrentUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveCurrentUserPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserByIdUseCase: ObserveCurrentUserByIdUseCase,
    private val observeCurrentUserPostsUseCase: ObserveCurrentUserPostsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        observeCurrentUserById()
    }

    private fun observeCurrentUserById() {

        viewModelScope.launch {

            observeCurrentUserByIdUseCase().collectLatest {
                _state.value = _state.value.copy(user = it, isLoading = true)
                observeCurrentUserPosts()
            }

        }

    }

    private fun observeCurrentUserPosts() {

        viewModelScope.launch {

            observeCurrentUserPostsUseCase().collectLatest {
                _state.value = _state.value.copy(posts = it, isLoading = false)
            }

        }

    }

    fun logout() {
        logoutUseCase()
    }

}