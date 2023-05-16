package com.jorgesanaguaray.fakesocialnetwork.postEdit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.postEdit.domain.PostEditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getPostById(id: Int) {

        viewModelScope.launch {

            postEditRepository.getPostById(id).onSuccess {

                _post.value = it

            }.onFailure {}

        }

    }

    fun updatePost(post: Post) {

        viewModelScope.launch {

            postEditRepository.updatePost(post)

        }

    }

    fun deletePostById(id: Int) {

        viewModelScope.launch {

            postEditRepository.deletePostById(id)

        }

    }

}