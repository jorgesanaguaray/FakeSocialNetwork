package com.jorgesanaguaray.fakesocialnetwork.home.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import com.jorgesanaguaray.fakesocialnetwork.home.domain.usecases.InsertPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

@HiltViewModel
class AddViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val insertPostUseCase: InsertPostUseCase
): ViewModel() {

    fun getCurrentUserId(): Int {
        return getCurrentUserIdUseCase()
    }

    fun insertPost(post: Post) {

        viewModelScope.launch {
            insertPostUseCase(post)
        }

    }

}