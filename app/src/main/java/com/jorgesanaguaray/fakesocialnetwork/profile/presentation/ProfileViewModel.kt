package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
class ProfileViewModel @Inject constructor(

    private val profileRepository: ProfileRepository

) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    fun getUserByUsername(username: String) {

        viewModelScope.launch {

            _profileState.update {
                it.copy(isContent = false, isLoading = true)
            }

            profileRepository.getUserByUsername(username).onSuccess { user ->

                _profileState.update {
                    it.copy(user = user, isContent = true, isLoading = false)
                }

            }.onFailure {}

        }

    }

    fun getUserWithPosts(userId: Int) {

        viewModelScope.launch {

            _profileState.update {
                it.copy(posts = profileRepository.getUserWithPosts(userId)!!.posts)
            }

        }

    }

}