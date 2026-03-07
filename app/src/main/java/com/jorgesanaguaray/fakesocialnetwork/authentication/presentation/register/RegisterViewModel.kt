package com.jorgesanaguaray.fakesocialnetwork.authentication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.InsertUserUseCase
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsUsernameAvailableUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.SaveLoginInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
    private val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    private val saveLoginInfoUseCase: SaveLoginInfoUseCase
) : ViewModel() {

    fun insertUser(user: User) {

        viewModelScope.launch {
            insertUserUseCase(user)
        }

    }

    fun isUsernameAvailable(username: String): Boolean {

        var usernameAvailable = false

        viewModelScope.launch {
            usernameAvailable = isUsernameAvailableUseCase(username)
        }

        return usernameAvailable

    }

    fun saveLoginInfo(id: Int, username: String, password: String) {
        saveLoginInfoUseCase(id, username, password)
    }

}