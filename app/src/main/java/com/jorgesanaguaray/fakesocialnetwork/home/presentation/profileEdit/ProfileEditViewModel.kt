package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profileEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.authentication.domain.usecases.IsUsernameAvailableUseCase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetCurrentUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getCurrentUserByIdUseCase: GetCurrentUserByIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    private val repository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: MutableLiveData<User?> get() = _user

    init {
        getCurrentUserById()
    }

    private fun getCurrentUserById() {

        viewModelScope.launch {
            _user.value = getCurrentUserByIdUseCase()
        }

    }

    fun getCurrentUserId(): Int {
        return getCurrentUserIdUseCase()
    }

    fun getCurrentUserUsername(): String {
        return repository.getCurrentUserUsername()
    }

    fun isUsernameAvailable(username: String): Boolean {
        return isUsernameAvailableUseCase(username)
    }

    fun updateUser(user: User) {

        viewModelScope.launch {
            repository.updateUser(user)
        }

    }

    fun saveLoginInfo(id: Int, username: String, password: String) {
        repository.saveLoginInfo(id, username, password)
    }

}