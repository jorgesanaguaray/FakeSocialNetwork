package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDomain
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.profile.domain.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    init {
        getPosts()
    }

    private fun getPosts() {

        viewModelScope.launch {

            _profileState.update {

                it.copy(

                    isContent = false,
                    isLoading = true

                )

            }

            profileRepository.getPosts().collectLatest { posts ->

                _profileState.update {

                    it.copy(

                        posts = posts,
                        isContent = true,
                        isLoading = false

                    )

                }

            }

        }

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

            val user: User = profileRepository.getUserById(id)
            user

        }

    }

    fun getUserByUsername(username: String) {

        viewModelScope.launch {

            _profileState.update {

                it.copy(

                    isContent = false,
                    isLoading = true

                )

            }

            _profileState.update {

                it.copy(

                    user = profileRepository.getUserByUsername(username),
                    isContent = true,
                    isLoading = false

                )

            }

        }

    }

}