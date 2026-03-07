package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.LogoutUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObserveUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObservePostsUseCase
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
    private val getUserIdUseCase: GetUserIdUseCase,
    private val observeUserByIdUseCase: ObserveUserByIdUseCase,
    private val observePostsUseCase: ObservePostsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        observeUserById()
    }

    private fun observeUserById() {

        viewModelScope.launch {

            observeUserByIdUseCase(getUserIdUseCase()).collectLatest {

                _profileState.value = _profileState.value.copy(user = it, isLoading = true)
                getPosts()

            }

        }

    }

    private fun getPosts() {

        viewModelScope.launch {

            observePostsUseCase().collectLatest {

                _profileState.value = _profileState.value.copy(posts = it, isLoading = false)

            }

        }

    }

    fun logout() {
        logoutUseCase()
    }

}