package com.jorgesanaguaray.fakesocialnetwork.home.presentation.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get()

        binding.mPost.setOnClickListener {
            validateCredentials()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateCredentials() {

        if (binding.mEditTextDescription.text.isNullOrEmpty()) {
            binding.mEditTextDescription.error = getString(R.string.enter_a_description)
        } else {
            insertPost()
        }

    }

    private fun insertPost() {

        val post = Post(
            id = null,
            description = binding.mEditTextDescription.text.toString().trim(),
            image = binding.mEditTextImageLink.text.toString().trim(),
            date = System.currentTimeMillis().toString(),
            userId = viewModel.getCurrentUserId()
        )

        viewModel.insertPost(post)
        clearViews()
        Toast.makeText(context, resources.getString(R.string.post_published), Toast.LENGTH_SHORT).show()

    }

    private fun clearViews() {
        binding.mEditTextDescription.setText("")
        binding.mEditTextImageLink.setText("")
    }

}