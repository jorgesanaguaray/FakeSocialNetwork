package com.jorgesanaguaray.fakesocialnetwork.home.presentation.postEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.repository.PostRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class PostEditViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val repository: PostRepository
) : ViewModel() {

    private val _post = MutableLiveData<Post?>()
    val post: LiveData<Post?> get() = _post

    fun getCurrentUserId(): Int {
        return getCurrentUserIdUseCase()
    }

    fun getPostById(id: Int) {

        viewModelScope.launch {
            _post.value = repository.getPostById(id)
        }

    }

    fun updatePost(post: Post) {

        viewModelScope.launch {
            repository.updatePost(post)
        }

    }

    fun deletePostById(id: Int) {

        viewModelScope.launch {
            repository.deletePostById(id)
        }

    }

}