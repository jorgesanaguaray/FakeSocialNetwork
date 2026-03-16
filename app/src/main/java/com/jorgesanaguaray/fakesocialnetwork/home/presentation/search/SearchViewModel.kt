package com.jorgesanaguaray.fakesocialnetwork.home.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.UserRepository
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetSearchedUsersUseCase
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
class SearchViewModel @Inject constructor(
    private val repository: UserRepository,
    private val getSearchedUsersUseCase: GetSearchedUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {

        viewModelScope.launch {

            _state.update {
                it.copy(isContent = false, isLoading = true)
            }

            _state.update {
                it.copy(users = repository.getUsers(), isContent = true, isLoading = false)
            }

        }

    }

    fun getSearchedUsers(query: String) {

        viewModelScope.launch {

            _state.update {
                it.copy(users = getSearchedUsersUseCase(query))
            }

        }

    }

}