package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jorgesanaguaray.fakesocialnetwork.Constants.Companion.KEY_POST_ID
import com.jorgesanaguaray.fakesocialnetwork.MainActivity
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.DialogProfileBinding
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var profileAdapter: ProfileAdapter
    private var posts: MutableList<Post> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeProfileState()
        retrieveUserId()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.mBottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        profileAdapter = ProfileAdapter { postId -> goPostEdit(postId) }
        binding.mRecyclerView.adapter = profileAdapter

        binding.mMenu.setOnClickListener {
            openMenu()
        }

        binding.mEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_mProfileNavigation_to_mProfileEditNavigation)
        }

    }

    private fun observeProfileState() {

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                profileViewModel.profileState.collect {

                    updateViews(it)

                }

            }

        }

    }

    private fun retrieveUserId() {

        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)
        profileViewModel.getUserById(userId)

    }

    private fun openMenu() {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val binding: DialogProfileBinding = DialogProfileBinding.inflate(layoutInflater)

        binding.apply {
            mClose.setOnClickListener { bottomSheetDialog.dismiss() }
            mLogout.setOnClickListener { logout() }
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()

    }

    private fun logout() {

        val sharedPreferencesA = activity?.getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        val editorA = sharedPreferencesA!!.edit()
        editorA.remove("id")
        editorA.remove("username")
        editorA.remove("password")
        editorA.apply()

        startActivity(Intent(context, MainActivity::class.java))
        requireActivity().finish()

    }

    private fun goPostEdit(id: Int) {
        val bundle = bundleOf(KEY_POST_ID to id)
        findNavController().navigate(R.id.action_mProfileNavigation_to_mPostEditNavigation, bundle)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateViews(profileState: ProfileState) {

        posts.clear()
        posts.addAll(profileState.posts.filter { it.userId == profileState.user?.id })

        profileAdapter.setUser(profileState.user)
        profileAdapter.setPosts(posts)
        profileAdapter.notifyDataSetChanged()

        binding.apply {

            mUsername.text = profileState.user?.username
            mPosts.text = posts.size.toString()
            mFollowers.text = numberFormat(profileState.user?.followers ?: 0)
            mFollowing.text = numberFormat(profileState.user?.following ?: 0)
            mProfilePicture.load(profileState.user?.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }

            mVerified.visibility = if (profileState.user?.isVerified == true) View.VISIBLE else View.GONE

            val isNameBioLinkEmpty = profileState.user?.name.isNullOrEmpty() && profileState.user?.bio.isNullOrEmpty() && profileState.user?.link.isNullOrEmpty()
            mContainerNameBioLink.visibility = if (isNameBioLinkEmpty) View.GONE else View.VISIBLE

            mName.visibility = if (profileState.user?.name.isNullOrEmpty()) View.GONE else View.VISIBLE
            mName.text = profileState.user?.name

            mBio.visibility = if (profileState.user?.bio.isNullOrEmpty()) View.GONE else View.VISIBLE
            mBio.text = profileState.user?.bio

            mLink.visibility = if (profileState.user?.link.isNullOrEmpty()) View.GONE else View.VISIBLE
            mLink.text = profileState.user?.link

            mNestedScrollView.visibility = if (profileState.isLoading) View.GONE else View.VISIBLE
            mProgressBar.visibility = if (profileState.isLoading) View.VISIBLE else View.GONE

        }

    }

    private fun numberFormat(likes: Long): String {

        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 1
        numberFormat.minimumFractionDigits = 0

        return when {
            likes < 1000 -> numberFormat.format(likes)
            likes < 1000000 -> numberFormat.format(likes / 1000.0) + "K"
            likes < 1000000000 -> numberFormat.format(likes / 1000000.0) + "M"
            else -> numberFormat.format(likes / 1000000000.0) + "B"
        }

    }

}