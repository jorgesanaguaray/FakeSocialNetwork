package com.jorgesanaguaray.fakesocialnetwork.core.domain

/**
 * Created by Jorge Sanaguaray
 */

data class User(

    val id: Int,
    val username: String,
    val name: String,
    val bio: String,
    val link: String,
    val password: String,
    val profilePicture: String,
    val followers: Long,
    val following: Long,
    val isVerified: Boolean

)