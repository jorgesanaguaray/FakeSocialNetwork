package com.jorgesanaguaray.fakesocialnetwork.profileEdit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.profileEdit.domain.ProfileEditRepository
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
class ProfileEditViewModel @Inject constructor(

    private val profileEditRepository: ProfileEditRepository

) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _profileEditState = MutableStateFlow(ProfileEditState())
    val profileEditState: StateFlow<ProfileEditState> = _profileEditState.asStateFlow()

    fun getUserById(id: Int) {

        viewModelScope.launch {

            _profileEditState.update {
                it.copy(isContent = false, isLoading = true)
            }

            profileEditRepository.getUserById(id).onSuccess { user ->

                _user.value = user

                _profileEditState.update {
                    it.copy(isContent = true, isLoading = false)
                }

            }.onFailure {}

        }

    }

    fun isUsernameAvailable(username: String) : Boolean {

        var result = false

        viewModelScope.launch {
            result = profileEditRepository.isUsernameAvailable(username)
        }

        return result

    }

    fun updateUser(user: User) {

        viewModelScope.launch {
            profileEditRepository.updateUser(user)
        }

    }

}