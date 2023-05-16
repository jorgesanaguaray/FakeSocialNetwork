package com.jorgesanaguaray.fakesocialnetwork.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.search.domain.SearchRepository
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

    private val searchRepository: SearchRepository

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

            searchRepository.getUsers().onSuccess { users ->

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

            searchRepository.getSearchedUsers(query).onSuccess { users ->

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