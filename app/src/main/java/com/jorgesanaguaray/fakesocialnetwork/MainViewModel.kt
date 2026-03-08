package com.jorgesanaguaray.fakesocialnetwork

import androidx.lifecycle.ViewModel
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
): ViewModel() {

    fun getUserId(): Int {
        return getCurrentUserIdUseCase()
    }

}