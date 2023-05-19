package com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Jorge Sanaguaray
 */

@Entity(
    tableName = "post_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val description: String,
    val image: String,
    val date: String,
    val userId: Int

)