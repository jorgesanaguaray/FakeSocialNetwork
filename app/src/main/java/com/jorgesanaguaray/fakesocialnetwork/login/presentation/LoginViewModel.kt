package com.jorgesanaguaray.fakesocialnetwork.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.login.domain.LoginRepository
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
class LoginViewModel @Inject constructor(

    private val loginRepository: LoginRepository

) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun isLoginSuccessful(username: String, password: String) {

        viewModelScope.launch {

            val loginSuccessful = loginRepository.isLoginSuccessful(username, password)

            _loginState.update {

                it.copy(isLoginSuccessful = loginSuccessful)

            }

        }

        getUserByUsernameAndPassword(username, password)

    }

    private fun getUserByUsernameAndPassword(username: String, password: String) {

        viewModelScope.launch {

            loginRepository.getUserByUsernameAndPassword(username, password).onSuccess {

                _user.value = it

            }.onFailure {}

        }

    }

}