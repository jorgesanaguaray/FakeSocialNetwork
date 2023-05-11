package com.jorgesanaguaray.fakesocialnetwork.add.data

import com.jorgesanaguaray.fakesocialnetwork.add.domain.AddRepository
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostDao
import com.jorgesanaguaray.fakesocialnetwork.core.data.mapper.toDatabase
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post

/**
 * Created by Jorge Sanaguaray
 */

class AddRepositoryImpl(private val postDao: PostDao) : AddRepository {

    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post.toDatabase())
    }

}