package com.jorgesanaguaray.fakesocialnetwork.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [UserEntity::class, PostEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val postDao: PostDao

}