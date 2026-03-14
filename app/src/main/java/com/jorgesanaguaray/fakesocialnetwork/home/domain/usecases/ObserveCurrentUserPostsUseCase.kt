package com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases

import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveCurrentUserPostsUseCase(
    private val repository: PostRepository,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) {

    operator fun invoke(): Flow<List<Post>> {

        return repository.observePosts().map { posts ->

            posts.filter { post ->
                post.userId == getCurrentUserIdUseCase()
            }

        }

    }

}