package com.jorgesanaguaray.fakesocialnetwork.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

/**
 * Created by Jorge Sanaguaray
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): UserEntity?

    @Transaction
    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserWithPosts(userId: Int): UserWithPosts?

}