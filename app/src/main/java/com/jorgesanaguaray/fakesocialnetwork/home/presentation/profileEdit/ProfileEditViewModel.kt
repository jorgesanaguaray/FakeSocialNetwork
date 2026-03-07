package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profileEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsUsernameAvailableUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.SaveLoginInfoUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val saveLoginInfoUseCase: SaveLoginInfoUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: MutableLiveData<User?> get() = _user

    init {
        getUserById()
    }

    private fun getUserById() {

        viewModelScope.launch {
            _user.value = getUserByIdUseCase(getUserIdUseCase())
        }

    }

    fun isUsernameAvailable(username: String) : Boolean {

        var usernameAvailable = false

        viewModelScope.launch {

            usernameAvailable = isUsernameAvailableUseCase(username)

        }

        return usernameAvailable

    }

    fun updateUser(user: User) {

        viewModelScope.launch {

            updateUserUseCase(user)

        }

    }

    fun saveLoginInfo(id: Int, username: String, password: String) {
        saveLoginInfoUseCase(id, username, password)
    }

}