package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository

class InsertPostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(post: Post) {
        repository.insertPost(post)
    }

}