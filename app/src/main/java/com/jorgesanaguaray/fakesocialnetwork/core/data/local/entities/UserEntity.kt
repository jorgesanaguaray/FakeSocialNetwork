package com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Jorge Sanaguaray
 */

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String,
    val name: String,
    val bio: String,
    val link: String,
    val password: String,
    val profilePicture: String,
    val isVerified: Boolean

)