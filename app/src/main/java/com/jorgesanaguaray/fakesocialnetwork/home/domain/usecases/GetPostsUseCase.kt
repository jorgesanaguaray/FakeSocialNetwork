package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class ObservePostsUseCase(
    private val repository: PostRepository
) {

    operator fun invoke(): Flow<List<Post>> {
        return repository.observePosts()
    }

}