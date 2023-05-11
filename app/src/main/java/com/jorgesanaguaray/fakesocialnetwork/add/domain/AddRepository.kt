package com.jorgesanaguaray.fakesocialnetwork.add.domain

import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post

/**
 * Created by Jorge Sanaguaray
 */

interface AddRepository {

    suspend fun insertPost(post: Post)

}