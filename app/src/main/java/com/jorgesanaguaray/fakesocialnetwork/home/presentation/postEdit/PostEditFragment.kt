package com.jorgesanaguaray.fakesocialnetwork.home.presentation.postEdit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorgesanaguaray.fakesocialnetwork.Constants.Companion.KEY_POST_ID
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentPostEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostEditFragment : Fragment() {

    private var _binding: FragmentPostEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PostEditViewModel
    private lateinit var navController: NavController

    private var postId = 0
    private var date = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get()
        navController = findNavController()

        postId = arguments?.getInt(KEY_POST_ID)!!

        viewModel.getPostById(postId)

        viewModel.post.observe(viewLifecycleOwner) { post ->

            if (post != null) {
                date = post.date
                setUpViews(post)
            }

        }

        binding.mBack.setOnClickListener {
            navController.navigateUp()
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

    @SuppressLint("SetTextI18n")
    private fun setUpViews(post: Post) {

        binding.apply {
            mEditTextDescription.setText(post.description)
            mEditTextImageLink.setText(post.image)
            mImagePost.load(post.image) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
                crossfade(400)
            }
        }

    }

    private fun validateCredentials() {

        if (binding.mEditTextDescription.text.isNullOrEmpty()) {
            binding.mEditTextDescription.error = getString(R.string.enter_a_description)
        } else {
            updatePost()
        }

    }

    private fun updatePost() {

        val post = Post(
            id = postId,
            description = binding.mEditTextDescription.text.toString().trim(),
            image = binding.mEditTextImageLink.text.toString().trim(),
            date = date,
            userId = viewModel.getCurrentUserId()
        )

        viewModel.updatePost(post)
        navController.navigateUp()
        Toast.makeText(context, resources.getString(R.string.post_updated), Toast.LENGTH_SHORT).show()

    }

    private fun deletePost() {
        viewModel.deletePostById(postId)
        navController.navigateUp()
        Toast.makeText(context, resources.getString(R.string.post_deleted), Toast.LENGTH_SHORT).show()
    }

}