package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostEntity
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

data class ProfileState(

    val user: User? = null,
    val posts: List<PostEntity> = emptyList(),
    val isContent: Boolean = false,
    val isLoading: Boolean = false

)