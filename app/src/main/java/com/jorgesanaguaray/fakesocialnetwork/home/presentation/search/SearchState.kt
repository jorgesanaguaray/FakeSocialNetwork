package com.jorgesanaguaray.fakesocialnetwork.home.presentation.search

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User

/**
 * Created by Jorge Sanaguaray
 */

data class SearchState(

    val users: List<User> = emptyList(),
    val isSearchView: Boolean = false,
    val isRecyclerView: Boolean = false,
    val isLoading: Boolean = false

)
