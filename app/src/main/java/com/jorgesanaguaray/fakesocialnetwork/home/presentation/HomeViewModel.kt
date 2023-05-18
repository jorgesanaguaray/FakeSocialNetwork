package com.jorgesanaguaray.fakesocialnetwork.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.home.domain.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val homeRepository: HomeRepository

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

                    isSearchView = false,
                    isRecyclerView = false,
                    isLoading = true

                )

            }

            homeRepository.getPosts().onSuccess { posts ->

                _homeState.update {

                    it.copy(

                        posts = posts,
                        isSearchView = true,
                        isRecyclerView = true,
                        isLoading = false

                    )

                }

            }.onFailure {}

        }

    }

    fun getSearchedPosts(query: String) {

        viewModelScope.launch {

            homeRepository.getSearchedPosts(query).onSuccess { posts ->

                _homeState.update {

                    it.copy(
                        posts = posts,
                        isSearchView = true,
                        isRecyclerView = true,
                        isLoading = false
                    )

                }

            }.onFailure {}

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

            val user: User = homeRepository.getUserById(id)
            user

        }

    }

}