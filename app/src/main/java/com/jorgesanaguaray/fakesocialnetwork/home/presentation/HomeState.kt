package com.jorgesanaguaray.fakesocialnetwork.home.presentation

import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post

/**
 * Created by Jorge Sanaguaray
 */

data class HomeState(

    val posts: List<Post> = emptyList(),
    val isContent: Boolean = false,
    val isLoading: Boolean = false

)