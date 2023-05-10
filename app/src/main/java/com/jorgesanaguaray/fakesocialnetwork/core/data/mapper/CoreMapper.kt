package com.jorgesanaguaray.fakesocialnetwork.core.data.mapper

import com.jorgesanaguaray.fakesocialnetwork.core.data.local.UserEntity
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User

/**
 * Created by Jorge Sanaguaray
 */

fun User.toDatabase(): UserEntity {

    return UserEntity(id, username, name, bio, link, password, profilePicture, isVerified)

}

fun UserEntity.toDomain(): User {

    return User(id, username, name, bio, link, password, profilePicture, isVerified)

}