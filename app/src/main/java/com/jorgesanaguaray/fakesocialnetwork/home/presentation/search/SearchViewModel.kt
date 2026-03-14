package com.jorgesanaguaray.fakesocialnetwork.home.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetSearchedUsersUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetUsersUseCase
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
    private val getUsersUseCase: GetUsersUseCase,
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
                it.copy(isSearchView = false, isRecyclerView = false, isLoading = true)
            }

            _state.update {
                it.copy(users = getUsersUseCase(), isSearchView = true, isRecyclerView = true, isLoading = false)
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