package com.jorgesanaguaray.fakesocialnetwork.profileEdit.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentProfileEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileEditViewModel: ProfileEditViewModel
    private lateinit var navController: NavController

    private var userId = 0
    private var username = ""
    private var profilePicture = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileEditViewModel = ViewModelProvider(this).get()
        navController = findNavController()

        // Get user id from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("id", 0)

        profileEditViewModel.getUserById(userId)

        profileEditViewModel.user.observe(viewLifecycleOwner) {

            username = it.username
            profilePicture = it.profilePicture
            setUpViews(it)

        }

        binding.mBack.setOnClickListener {
            navController.navigateUp()
        }

        binding.mEditProfilePicture.setOnClickListener {

            ImagePicker.with(requireActivity()).crop().cropOval().createIntentFromDialog {
                launcherProfilePicture.launch(it)
            }

        }

        binding.mUpdate.setOnClickListener {
            validateCredentials()
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

    private fun setUpViews(user: User) {

        binding.apply {

            mProfilePicture.load(user.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }
            mEditTextUsername.setText(user.username)
            mEditTextName.setText(user.name)
            mEditTextBio.setText(user.bio)
            mEditTextLink.setText(user.link)
            mEditTextFollowers.setText(user.followers.toString())
            mEditTextFollowing.setText(user.following.toString())
            mEditTextPassword.setText(user.password)
            mSwitch.isChecked = user.isVerified

        }

    }

    private fun validateCredentials() {

        when {

            TextUtils.isEmpty(binding.mEditTextUsername.text.toString()) -> {
                binding.mEditTextUsername.error = resources.getString(R.string.enter_a_username)
            }

            TextUtils.isEmpty(binding.mEditTextPassword.text.toString()) -> {
                binding.mEditTextPassword.error = resources.getString(R.string.enter_a_password)
            }

            binding.mEditTextPassword.text.toString().length < 6 -> {
                binding.mEditTextPassword.error = resources.getString(R.string.password_must_be_6_or_more_characters)
            }

            else -> {

                if (username == binding.mEditTextUsername.text.toString()) {

                    updateUser()

                } else {

                    isUsernameAvailable()

                }

            }

        }

    }

    private fun isUsernameAvailable() {

        if (profileEditViewModel.isUsernameAvailable(binding.mEditTextUsername.text.toString())) {
            updateUser()
        } else {
            binding.mEditTextUsername.error = resources.getString(R.string.username_not_available_try_another)
        }

    }

    private fun updateUser() {

        val user = User(
            id = userId,
            username = binding.mEditTextUsername.text.toString(),
            name = binding.mEditTextName.text.toString().trim(),
            bio = binding.mEditTextBio.text.toString(),
            link = binding.mEditTextLink.text.toString().trim(),
            password = binding.mEditTextPassword.text.toString(),
            profilePicture = profilePicture,
            followers = binding.mEditTextFollowers.text.toString().toLong(),
            following = binding.mEditTextFollowing.text.toString().toLong(),
            isVerified = binding.mSwitch.isChecked
        )

        profileEditViewModel.updateUser(user)
        saveUserInfo()
        navController.navigateUp()
        Toast.makeText(context, resources.getString(R.string.updated_user), Toast.LENGTH_SHORT).show()

    }

    private fun saveUserInfo() {

        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putString("username", binding.mEditTextUsername.text.toString())
        editor.putString("password", binding.mEditTextPassword.text.toString())
        editor.apply()

    }

    private var launcherProfilePicture: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {

            profilePicture = it.data?.data!!.toString()

            binding.mProfilePicture.load(profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }

        }

    }

}