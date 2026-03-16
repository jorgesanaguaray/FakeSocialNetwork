package com.jorgesanaguaray.fakesocialnetwork.home.presentation.home

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post

data class HomeState(

    val posts: List<Post> = emptyList(),
    val isContainer: Boolean = false,
    val isLoading: Boolean = false

)