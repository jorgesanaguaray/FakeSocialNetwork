package com.jorgesanaguaray.fakesocialnetwork.core.data.mapper

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.PostEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

fun User.toDatabase(): UserEntity {

    return UserEntity(id, username, name, bio, link, password, profilePicture, followers, following, isVerified)

}

fun UserEntity.toDomain(): User {

    return User(id, username, name, bio, link, password, profilePicture, followers, following, isVerified)

}

fun Post.toDatabase(): PostEntity {

    return PostEntity(id, description, image, date, userId)

}

fun PostEntity.toDomain(): Post {

    return Post(id, description, image, date, userId)

}