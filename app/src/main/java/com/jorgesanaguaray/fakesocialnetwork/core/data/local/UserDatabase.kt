package com.jorgesanaguaray.fakesocialnetwork.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.dao.UserDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.PostEntity
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.entities.UserEntity

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [UserEntity::class, PostEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val postDao: PostDao

}