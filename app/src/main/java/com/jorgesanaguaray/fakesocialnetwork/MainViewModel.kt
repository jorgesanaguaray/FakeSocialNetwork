package com.jorgesanaguaray.fakesocialnetwork

import androidx.lifecycle.ViewModel
import com.jorgesanaguaray.fakesocialnetwork.core.domain.usecases.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
): ViewModel() {

    fun getUserId(): Int {
        return getUserIdUseCase()
    }

}