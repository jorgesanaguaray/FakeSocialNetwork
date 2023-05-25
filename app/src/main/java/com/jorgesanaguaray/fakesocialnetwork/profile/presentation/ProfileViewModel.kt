package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository
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

    private val profileRepository: ProfileRepository

) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    fun getUserById(id: Int) {

        viewModelScope.launch {

            profileRepository.getUserById(id).collectLatest {

                _profileState.value = _profileState.value.copy(user = it, isLoading = true)
                getPosts()

            }

        }

    }

    private fun getPosts() {

        viewModelScope.launch {

            profileRepository.getPosts().collectLatest {

                _profileState.value = _profileState.value.copy(posts = it, isLoading = false)

            }

        }

    }

}