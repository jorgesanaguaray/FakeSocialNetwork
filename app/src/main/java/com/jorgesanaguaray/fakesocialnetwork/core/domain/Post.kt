package com.jorgesanaguaray.fakesocialnetwork.core.domain

/**
 * Created by Jorge Sanaguaray
 */

data class Post(

    val id: Int? = null,
    val description: String,
    val image: String,
    val date: String,
    val likes: Long,
    val comments: Long,
    val shares: Long,
    val userId: Int

)