package com.jorgesanaguaray.fakesocialnetwork.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Created by Jorge Sanaguaray
 */

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(postEntity: PostEntity)

    @Update
    suspend fun updatePost(postEntity: PostEntity)

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM post_table WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity

    @Query("DELETE FROM post_table WHERE id = :id")
    suspend fun deletePostById(id: Int)

}