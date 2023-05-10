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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.CircleCropTransformation
import com.github.drjacky.imagepicker.ImagePicker
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentProfileEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileEditViewModel: ProfileEditViewModel

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

        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_id), Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("id", 0)

        profileEditViewModel.getUserById(userId)

        profileEditViewModel.user.observe(viewLifecycleOwner) {

            username = it.username
            profilePicture = it.profilePicture

            binding.apply {

                mProfilePicture.load(it.profilePicture) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_profile)
                    error(R.drawable.ic_profile)
                    crossfade(true)
                    crossfade(400)
                }
                mEditTextUsername.setText(it.username)
                mEditTextName.setText(it.name)
                mEditTextBio.setText(it.bio)
                mEditTextLink.setText(it.link)
                mEditTextPassword.setText(it.password)
                mSwitch.isChecked = it.isVerified

            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileEditViewModel.profileEditState.collect {
                    setUpViews(it)
                }
            }
        }

        binding.mCamera.setOnClickListener {

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

    private fun setUpViews(profileEditState: ProfileEditState) {

        if (profileEditState.isContent) binding.mContent.visibility = View.VISIBLE
        else binding.mContent.visibility = View.GONE

        if (profileEditState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

    private fun saveClick() {

        binding.mSave.setOnClickListener {

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

    }

    private fun isUsernameAvailable() {

        val result = profileEditViewModel.isUsernameAvailable(binding.mEditTextUsername.text.toString())

        if (result) {
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
            bio = binding.mEditTextBio.text.toString().trim(),
            link = binding.mEditTextLink.text.toString().trim(),
            password = binding.mEditTextPassword.text.toString(),
            profilePicture = profilePicture,
            isVerified = binding.mSwitch.isChecked
        )

        profileEditViewModel.updateUser(user)
        saveLoginInfo()
        Toast.makeText(context, "Updated user.", Toast.LENGTH_SHORT).show()

    }

    private fun saveLoginInfo() {

        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.login_info), Context.MODE_PRIVATE)
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