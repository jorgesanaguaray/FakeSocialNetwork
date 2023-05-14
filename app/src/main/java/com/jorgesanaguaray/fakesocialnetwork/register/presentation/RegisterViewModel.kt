package com.jorgesanaguaray.fakesocialnetwork.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.register.domain.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(

    private val registerRepository: RegisterRepository

) : ViewModel() {

    fun insertUser(user: User) {

        viewModelScope.launch {

            registerRepository.insertUser(user)

        }

    }

    fun isUsernameAvailable(username: String) : Boolean {

        var usernameAvailable = false

        viewModelScope.launch {

            usernameAvailable = registerRepository.isUsernameAvailable(username)

        }

        return usernameAvailable

    }

}