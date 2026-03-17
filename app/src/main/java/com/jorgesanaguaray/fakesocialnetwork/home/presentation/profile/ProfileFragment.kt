package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
import com.jorgesanaguaray.fakesocialnetwork.databinding.DialogProfileBinding
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get()
        adapter = ProfileAdapter { postId -> goPostEdit(postId) }

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.mBottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    setUpViews(state)
                }
            }
        }

        adapter = ProfileAdapter { postId -> goPostEdit(postId) }
        binding.mRecyclerView.adapter = adapter

        binding.mMenu.setOnClickListener {
            openMenu()
        }

        binding.mEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_mProfileNavigation_to_mProfileEditNavigation)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        viewModel.logout()
        startActivity(Intent(context, MainActivity::class.java))
        requireActivity().finish()
    }

    private fun goPostEdit(id: Int) {
        val bundle = bundleOf(KEY_POST_ID to id)
        findNavController().navigate(R.id.action_mProfileNavigation_to_mPostEditNavigation, bundle)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun setUpViews(state: ProfileState) {

        adapter.setUser(state.user)
        adapter.setPosts(state.posts)
        adapter.notifyDataSetChanged()

        binding.apply {

            if (state.user != null) {

                mUsername.text = state.user.username
                mPosts.text = state.posts.size.toString()
                mProfilePicture.load(state.user.profilePicture) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_profile)
                    error(R.drawable.ic_profile)
                    crossfade(true)
                    crossfade(400)
                }

                mVerified.visibility = if (state.user.isVerified) View.VISIBLE else View.GONE

                val isNameBioLinkEmpty = state.user.name.isEmpty() && state.user.bio.isEmpty() && state.user.link.isEmpty()
                mContainerNameBioLink.visibility = if (isNameBioLinkEmpty) View.GONE else View.VISIBLE

                mName.visibility = if (state.user.name.isEmpty()) View.GONE else View.VISIBLE
                mName.text = state.user.name

                mBio.visibility = if (state.user.bio.isEmpty()) View.GONE else View.VISIBLE
                mBio.text = state.user.bio

                mLink.visibility = if (state.user.link.isEmpty()) View.GONE else View.VISIBLE
                mLink.text = state.user.link

            }

            if (state.isContent) mContent.visibility = View.VISIBLE
            else mContent.visibility = View.GONE

            if (state.isLoading) mProgressBar.visibility = View.VISIBLE
            else mProgressBar.visibility = View.GONE

        }

    }

}