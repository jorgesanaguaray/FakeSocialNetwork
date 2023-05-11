package com.jorgesanaguaray.fakesocialnetwork.add.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.add.domain.AddRepository
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class AddViewModel @Inject constructor(

    private val addRepository: AddRepository

) : ViewModel() {

    fun insertPost(post: Post) {

        viewModelScope.launch {

            addRepository.insertPost(post)

        }

    }

}