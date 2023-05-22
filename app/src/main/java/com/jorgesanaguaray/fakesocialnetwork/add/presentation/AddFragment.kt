package com.jorgesanaguaray.fakesocialnetwork.add.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import coil.load
import com.github.drjacky.imagepicker.ImagePicker
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var addViewModel: AddViewModel
    private var imagePost = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addViewModel = ViewModelProvider(this).get()

        binding.mImagePost.setOnClickListener {

            ImagePicker.with(requireActivity()).crop().createIntentFromDialog {
                launcherImage.launch(it)
            }

        }

        binding.mPost.setOnClickListener {
            validateCredentials()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateCredentials() {

        when {

            TextUtils.isEmpty(binding.mEditTextDescription.text.toString()) -> {
                binding.mEditTextDescription.error = resources.getString(R.string.enter_a_description)
            }

            TextUtils.isEmpty(binding.mEditTextLikes.text.toString()) -> {
                binding.mEditTextLikes.error = resources.getString(R.string.enter_a_number_of_likes)
            }

            TextUtils.isEmpty(binding.mEditTextComments.text.toString()) -> {
                binding.mEditTextComments.error = resources.getString(R.string.enter_a_number_of_comments)
            }

            TextUtils.isEmpty(binding.mEditTextShares.text.toString()) -> {
                binding.mEditTextShares.error = resources.getString(R.string.enter_a_number_of_shares)
            }

            else -> {

                insertPost()

            }

        }

    }

    private fun insertPost() {

        // Get user id from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)

        val post = Post(
            id = null,
            description = binding.mEditTextDescription.text.toString().trim(),
            image = imagePost,
            date = System.currentTimeMillis().toString(),
            likes = binding.mEditTextLikes.text.toString().toLong(),
            comments = binding.mEditTextComments.text.toString().toLong(),
            shares = binding.mEditTextShares.text.toString().toLong(),
            userId = userId
        )

        addViewModel.insertPost(post)
        clearViews()
        Toast.makeText(context, resources.getString(R.string.post_published), Toast.LENGTH_SHORT).show()

    }

    private fun clearViews() {

        binding.mEditTextDescription.setText("")
        binding.mEditTextLikes.setText("")
        binding.mEditTextComments.setText("")
        binding.mEditTextShares.setText("")
        binding.mImagePost.setImageResource(R.drawable.ic_add)

    }

    private var launcherImage: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {

            imagePost = it.data?.data!!.toString()

            binding.mImagePost.load(imagePost) {
                placeholder(R.drawable.ic_add)
                error(R.drawable.ic_add)
                crossfade(true)
                crossfade(400)
            }

        }

    }

}