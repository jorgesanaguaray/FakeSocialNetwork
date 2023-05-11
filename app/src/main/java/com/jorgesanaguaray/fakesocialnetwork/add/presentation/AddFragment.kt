package com.jorgesanaguaray.fakesocialnetwork.add.presentation

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addViewModel = ViewModelProvider(this).get()

        postClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun postClick() {

        binding.mPost.setOnClickListener {

            when {

                TextUtils.isEmpty(binding.mEditTextDescription.text.toString()) -> {
                    binding.mEditTextDescription.error = resources.getString(R.string.enter_a_description)
                }

                else -> {

                    insertPost()

                }

            }

        }

    }

    private fun insertPost() {

        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)

        val post = Post(
            id = null,
            description = binding.mEditTextDescription.text.toString().trim(),
            userId = userId
        )

        addViewModel.insertPost(post)
        Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show()

    }

}