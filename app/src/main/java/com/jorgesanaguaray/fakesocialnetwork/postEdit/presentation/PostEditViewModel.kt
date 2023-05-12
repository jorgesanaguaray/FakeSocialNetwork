package com.jorgesanaguaray.fakesocialnetwork.postEdit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.postEdit.domain.PostEditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class PostEditViewModel @Inject constructor(

    private val postEditRepository: PostEditRepository

) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    private val _postEditState = MutableStateFlow(PostEditState())
    val postEditState: StateFlow<PostEditState> = _postEditState.asStateFlow()

    fun getPostById(id: Int) {

        viewModelScope.launch {

            _postEditState.update {
                it.copy(isContent = false, isLoading = true)
            }

            postEditRepository.getPostById(id).onSuccess { post ->

                _post.value = post

                _postEditState.update {
                    it.copy(isContent = true, isLoading = false)
                }

            }.onFailure {}

        }

    }

    fun updatePost(post: Post) {

        viewModelScope.launch {
            postEditRepository.updatePost(post)
        }

    }

}