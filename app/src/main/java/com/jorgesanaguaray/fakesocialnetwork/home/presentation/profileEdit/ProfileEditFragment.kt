package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profileEdit

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentProfileEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileEditViewModel
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get()
        navController = findNavController()

        viewModel.user.observe(viewLifecycleOwner) {
            setUpViews(it!!)
        }

        binding.mBack.setOnClickListener {
            navController.navigateUp()
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

    @SuppressLint("SetTextI18n")
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
            mEditTextLinkProfilePicture.setText(user.profilePicture)
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

                if (viewModel.getCurrentUserUsername() == binding.mEditTextUsername.text.toString()) {
                    updateUser()
                } else {
                    isUsernameAvailable()
                }

            }

        }

    }

    private fun isUsernameAvailable() {

        if (viewModel.isUsernameAvailable(binding.mEditTextUsername.text.toString())) {
            updateUser()
        } else {
            binding.mEditTextUsername.error = resources.getString(R.string.username_not_available_try_another)
        }

    }

    private fun updateUser() {

        val user = User(
            id = viewModel.getCurrentUserId(),
            username = binding.mEditTextUsername.text.toString(),
            name = binding.mEditTextName.text.toString().trim(),
            bio = binding.mEditTextBio.text.toString(),
            link = binding.mEditTextLink.text.toString().trim(),
            password = binding.mEditTextPassword.text.toString(),
            profilePicture = binding.mEditTextLinkProfilePicture.text.toString().trim(),
            isVerified = binding.mSwitch.isChecked
        )

        viewModel.updateUser(user)
        saveUserInfo()
        navController.navigateUp()
        Toast.makeText(context, resources.getString(R.string.updated_user), Toast.LENGTH_SHORT).show()

    }

    private fun saveUserInfo() {

        viewModel.saveLoginInfo(
            id = viewModel.getCurrentUserId(),
            username = binding.mEditTextUsername.text.toString(),
            password = binding.mEditTextPassword.text.toString()
        )

    }

}