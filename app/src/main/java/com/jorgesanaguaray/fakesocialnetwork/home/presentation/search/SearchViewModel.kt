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

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {

        viewModelScope.launch {

            _searchState.update {

                it.copy(

                    isSearchView = false,
                    isRecyclerView = false,
                    isLoading = true

                )

            }

            getUsersUseCase().onSuccess { users ->

                _searchState.update {

                    it.copy(

                        users = users,
                        isSearchView = true,
                        isRecyclerView = true,
                        isLoading = false

                    )

                }

            }.onFailure {}

        }

    }

    fun getSearchedUsers(query: String) {

        viewModelScope.launch {

            getSearchedUsersUseCase(query).onSuccess { users ->

                _searchState.update {

                    it.copy(
                        users = users,
                        isSearchView = true,
                        isRecyclerView = true,
                        isLoading = false
                    )

                }

            }.onFailure {}

        }

    }

}