package com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Jorge Sanaguaray
 */

@Entity(tableName = "post_table")
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val description: String,
    val image: String,
    val date: String,
    val likes: Long,
    val comments: Long,
    val shares: Long,
    val userId: Int

)