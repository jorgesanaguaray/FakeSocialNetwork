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

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Show BottomNavigationView
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.mBottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        // Inflate fragment layout
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(this).get()
        profileAdapter = ProfileAdapter(editClick = { goPostEdit(it) })

        // Get username from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.login_info), Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        profileViewModel.getUserByUsername(username!!)

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.CREATED) {

                profileViewModel.profileState.collect {

                    setUpViews(it)
                    saveUserId(it)

                }

            }

        }

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

        // Delete login info
        val sharedPreferencesA = activity?.getSharedPreferences(getString(R.string.login_info), Context.MODE_PRIVATE)
        val editorA = sharedPreferencesA!!.edit()
        editorA.remove("username")
        editorA.remove("password")
        editorA.apply()

        // Delete user id
        val sharedPreferencesB = activity?.getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        val editorB = sharedPreferencesB!!.edit()
        editorB.remove("id")
        editorB.apply()

        // Go MainActivity
        startActivity(Intent(context, MainActivity::class.java))
        requireActivity().finish()

    }

    private fun goPostEdit(id: Int) {
        val bundle = bundleOf(KEY_POST_ID to id)
        findNavController().navigate(R.id.action_mProfileNavigation_to_mPostEditNavigation, bundle)
    }

    private fun setUpViews(profileState: ProfileState) {

        binding.apply {

            mUsername.text = profileState.user!!.username
            mName.text = profileState.user.name
            mBio.text = profileState.user.bio
            mLink.text = profileState.user.link
            mProfilePicture.load(profileState.user.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }

            if (profileState.user.isVerified) mVerified.visibility = View.VISIBLE
            else mVerified.visibility = View.GONE

        }

        profileAdapter.setUser(profileState.user!!)
        profileAdapter.setPosts(profileState.posts)
        binding.mRecyclerView.adapter = profileAdapter

        if (profileState.isContent) binding.mNestedScroll.visibility = View.VISIBLE
        else binding.mNestedScroll.visibility = View.GONE

        if (profileState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

    private fun saveUserId(profileState: ProfileState) {

        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt("id", profileState.user!!.id!!)
        editor.apply()

    }

}