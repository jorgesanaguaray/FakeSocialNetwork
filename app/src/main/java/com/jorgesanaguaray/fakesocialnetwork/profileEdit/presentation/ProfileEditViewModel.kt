package com.jorgesanaguaray.fakesocialnetwork.profileEdit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.profileEdit.domain.ProfileEditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getUserById(id: Int) {

        viewModelScope.launch {

            profileEditRepository.getUserById(id).onSuccess {

                _user.value = it

            }.onFailure {}

        }

    }

    fun isUsernameAvailable(username: String) : Boolean {

        var usernameAvailable = false

        viewModelScope.launch {

            usernameAvailable = profileEditRepository.isUsernameAvailable(username)

        }

        return usernameAvailable

    }

    fun updateUser(user: User) {

        viewModelScope.launch {

            profileEditRepository.updateUser(user)

        }

    }

}