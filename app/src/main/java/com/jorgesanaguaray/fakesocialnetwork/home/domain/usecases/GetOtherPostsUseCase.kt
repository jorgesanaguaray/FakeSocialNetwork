package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository

class GetOtherPostsUseCase(
    private val repository: PostRepository
) {

    private lateinit var posts: MutableList<Post>

    suspend operator fun invoke(currentUserId: Int): List<Post> {

        posts = ArrayList()

        repository.getPosts().forEach {
            if (it.userId != currentUserId) {
                posts.add(it)
            }
        }

        return posts

    }

}