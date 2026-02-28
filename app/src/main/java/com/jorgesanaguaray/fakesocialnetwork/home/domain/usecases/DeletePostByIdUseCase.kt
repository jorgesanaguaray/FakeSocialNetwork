package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository

class DeletePostByIdUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(id: Int) {
        repository.deletePostById(id)
    }

}