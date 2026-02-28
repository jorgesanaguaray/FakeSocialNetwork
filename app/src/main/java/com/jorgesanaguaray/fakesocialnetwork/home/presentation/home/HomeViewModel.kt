package com.jorgesanaguaray.fakesocialnetwork.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUserByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.ObservePostsUseCase
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
class HomeViewModel @Inject constructor(
    private val observePostsUseCase: ObservePostsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        getPosts()
    }

    fun getPosts() {

        viewModelScope.launch {

            _homeState.update {

                it.copy(

                    isContent = false,
                    isLoading = true

                )

            }

            observePostsUseCase().collectLatest { posts ->

                _homeState.update {

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

            val user: User = getUserByIdUseCase(id)
            user

        }

    }

}