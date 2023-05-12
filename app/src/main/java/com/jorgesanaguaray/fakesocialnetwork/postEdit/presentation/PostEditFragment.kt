package com.jorgesanaguaray.fakesocialnetwork.postEdit.presentation

import android.annotation.SuppressLint
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorgesanaguaray.fakesocialnetwork.Constants.Companion.KEY_POST_ID
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentPostEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostEditFragment : Fragment() {

    private var _binding: FragmentPostEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var postEditViewModel: PostEditViewModel

    private var postId = 0
    private var imagePost = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Hide BottomNavigationView
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.mBottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        // Inflate fragment layout
        _binding = FragmentPostEditBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postEditViewModel = ViewModelProvider(this).get()
        postId = arguments?.getInt(KEY_POST_ID)!!

        postEditViewModel.post.observe(viewLifecycleOwner) {

            imagePost = it.image

            binding.apply {

                mEditTextDescription.setText(it.description)
                mImagePost.load(it.image) {
                    placeholder(R.drawable.ic_profile)
                    error(R.drawable.ic_profile)
                    crossfade(true)
                    crossfade(400)
                }

            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postEditViewModel.postEditState.collect {
                    setUpViews(it)
                }
            }
        }

        postEditViewModel.getPostById(postId)

        binding.mImagePost.setOnClickListener {

            ImagePicker.with(requireActivity()).crop().createIntentFromDialog {
                launcherProfilePicture.launch(it)
            }

        }

        saveClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews(postEditState: PostEditState) {

        if (postEditState.isContent) binding.mScrollView.visibility = View.VISIBLE
        else binding.mScrollView.visibility = View.GONE

        if (postEditState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

    private fun saveClick() {

        binding.mSave.setOnClickListener {

            when {

                TextUtils.isEmpty(binding.mEditTextDescription.text.toString()) -> {
                    binding.mEditTextDescription.error = resources.getString(R.string.enter_a_description)
                }

                else -> {

                    updatePost()

                }

            }

        }

    }

    private fun updatePost() {

        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)

        val post = Post(
            id = postId,
            description = binding.mEditTextDescription.text.toString().trim(),
            image = imagePost,
            date = System.currentTimeMillis().toString(),
            userId = userId
        )

        postEditViewModel.updatePost(post)
        Toast.makeText(context, "Post saved", Toast.LENGTH_SHORT).show()

    }

    private var launcherProfilePicture: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {

            imagePost = it.data?.data!!.toString()

            binding.mImagePost.load(imagePost) {
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }

        }

    }

}