package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository

class GetPostByIdUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(id: Int): Result<Post> {
        return repository.getPostById(id)
    }

}