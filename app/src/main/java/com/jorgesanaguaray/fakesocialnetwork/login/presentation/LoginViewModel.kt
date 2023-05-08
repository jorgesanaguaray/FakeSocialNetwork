package com.jorgesanaguaray.fakesocialnetwork.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.login.domain.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val loginRepository: LoginRepository

) : ViewModel() {

    fun isLoginSuccessful(username: String, password: String): Boolean {

        var result = false

        viewModelScope.launch {
            result = loginRepository.isLoginSuccessful(username, password)
        }

        return result

    }

}