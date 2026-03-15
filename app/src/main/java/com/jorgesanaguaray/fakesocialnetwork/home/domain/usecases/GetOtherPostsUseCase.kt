package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase

class GetOtherPostsUseCase(
    private val repository: PostRepository,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
) {

    private lateinit var posts: MutableList<Post>

    suspend operator fun invoke(): List<Post> {

        posts = ArrayList()

        repository.getPosts().forEach {
            if (it.userId != getCurrentUserIdUseCase()) {
                posts.add(it)
            }
        }

        return posts

    }

}