package com.jorgesanaguaray.fakesocialnetwork.postEdit.presentation

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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorgesanaguaray.fakesocialnetwork.Constants.Companion.KEY_POST_ID
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentPostEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostEditFragment : Fragment() {

    private var _binding: FragmentPostEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var postEditViewModel: PostEditViewModel
    private lateinit var navController: NavController

    private var postId = 0
    private var date = ""
    private var imagePost = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postEditViewModel = ViewModelProvider(this).get()
        navController = findNavController()

        postId = arguments?.getInt(KEY_POST_ID)!!

        postEditViewModel.getPostById(postId)

        postEditViewModel.post.observe(viewLifecycleOwner) {

            date = it.date
            imagePost = it.image
            setUpViews(it)

        }

        binding.mBack.setOnClickListener {
            navController.navigateUp()
        }

        binding.mImagePost.setOnClickListener {

            ImagePicker.with(requireActivity()).crop().createIntentFromDialog {
                launcherImage.launch(it)
            }

        }

        binding.mUpdate.setOnClickListener {
            validateCredentials()
        }

        binding.mDelete.setOnClickListener {
            deletePost()
        }

    }

    override fun onResume() {
        super.onResume()

        // Hide Bottom Navigation View
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.mBottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews(post: Post) {

        binding.apply {

            mEditTextDescription.setText(post.description)
            mEditTextLikes.setText(post.likes.toString())
            mEditTextComments.setText(post.comments.toString())
            mEditTextShares.setText(post.shares.toString())
            mImagePost.load(post.image) {
                placeholder(R.drawable.ic_add)
                error(R.drawable.ic_add)
                crossfade(true)
                crossfade(400)
            }

        }

    }

    private fun validateCredentials() {

        when {

            TextUtils.isEmpty(binding.mEditTextDescription.text.toString()) -> {
                binding.mEditTextDescription.error = resources.getString(R.string.enter_a_description)
            }

            else -> {

                updatePost()

            }

        }

    }

    private fun updatePost() {

        // Get user id from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)

        val post = Post(
            id = postId,
            description = binding.mEditTextDescription.text.toString().trim(),
            image = imagePost,
            date = date,
            likes = binding.mEditTextLikes.text.toString().toLong(),
            comments = binding.mEditTextComments.text.toString().toLong(),
            shares = binding.mEditTextShares.text.toString().toLong(),
            userId = userId
        )

        postEditViewModel.updatePost(post)
        navController.navigateUp()
        Toast.makeText(context, resources.getString(R.string.post_updated), Toast.LENGTH_SHORT).show()

    }

    private fun deletePost() {

        postEditViewModel.deletePostById(postId)
        navController.navigateUp()
        Toast.makeText(context, resources.getString(R.string.post_deleted), Toast.LENGTH_SHORT).show()

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