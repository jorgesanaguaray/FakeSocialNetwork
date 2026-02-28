package com.jorgesanaguaray.fakesocialnetwork.authentication.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.GetUserByUsernameAndPasswordUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsLoginSuccessfulUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
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
    private val isLoginSuccessfulUseCase: IsLoginSuccessfulUseCase,
    private val getUserByUsernameAndPasswordUseCase: GetUserByUsernameAndPasswordUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun isLoginSuccessful(username: String, password: String) {

        viewModelScope.launch {

            val loginSuccessful = isLoginSuccessfulUseCase(username, password)

            _loginState.update {

                it.copy(isLoginSuccessful = loginSuccessful)

            }

        }

        getUserByUsernameAndPassword(username, password)

    }

    private fun getUserByUsernameAndPassword(username: String, password: String) {

        viewModelScope.launch {

            getUserByUsernameAndPasswordUseCase(username, password).onSuccess {

                _user.value = it

            }.onFailure {}

        }

    }

}