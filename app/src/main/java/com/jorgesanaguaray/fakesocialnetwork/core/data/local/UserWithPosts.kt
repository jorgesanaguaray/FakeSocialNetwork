package com.jorgesanaguaray.fakesocialnetwork.core.data.local

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Jorge Sanaguaray
 */

data class UserWithPosts(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val posts: List<PostEntity>
)
