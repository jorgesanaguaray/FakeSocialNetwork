package com.jorgesanaguaray.fakesocialnetwork.home.presentation.postEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.DeletePostByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.GetPostByIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.UpdatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class PostEditViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase
) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    fun getUserId(): Int {
        return getCurrentUserIdUseCase()
    }

    fun getPostById(id: Int) {

        viewModelScope.launch {

            getPostByIdUseCase(id).onSuccess {

                _post.value = it

            }.onFailure {}

        }

    }

    fun updatePost(post: Post) {

        viewModelScope.launch {

            updatePostUseCase(post)

        }

    }

    fun deletePostById(id: Int) {

        viewModelScope.launch {

            deletePostByIdUseCase(id)

        }

    }

}