package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profile

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User

/**
 * Created by Jorge Sanaguaray
 */

data class ProfileState(

    val user: User? = null,
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false

)