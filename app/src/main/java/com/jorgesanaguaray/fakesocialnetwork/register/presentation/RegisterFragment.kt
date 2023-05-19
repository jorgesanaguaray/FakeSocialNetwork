package com.jorgesanaguaray.fakesocialnetwork.register.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.SecondActivity
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        registerViewModel = ViewModelProvider(this).get()

    }

    override fun onResume() {
        super.onResume()

        binding.mBack.setOnClickListener {
            findNavController().navigate(R.id.action_mRegisterNavigation_to_mLoginNavigation)
        }

        binding.mSignUp.setOnClickListener {
            validateRegisterCredentials()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateRegisterCredentials() {

        when {

            TextUtils.isEmpty(binding.mEditTextUsername.text.toString()) -> {
                binding.mEditTextUsername.error = resources.getString(R.string.enter_a_username)
            }

            TextUtils.isEmpty(binding.mEditTextName.text.toString()) -> {
                binding.mEditTextName.error = resources.getString(R.string.enter_a_name)
            }

            TextUtils.isEmpty(binding.mEditTextPassword.text.toString()) -> {
                binding.mEditTextPassword.error = resources.getString(R.string.enter_a_password)
            }

            binding.mEditTextPassword.text.toString().length < 6 -> {
                binding.mEditTextPassword.error = resources.getString(R.string.password_must_be_6_or_more_characters)
            }

            else -> {

                isUsernameAvailable()

            }

        }

    }

    private fun isUsernameAvailable() {

        if (registerViewModel.isUsernameAvailable(binding.mEditTextUsername.text.toString())) {
            insertUser()
        } else {
            binding.mEditTextUsername.error = resources.getString(R.string.username_not_available_try_another)
        }

    }

    private fun insertUser() {

        val userId = UUID.randomUUID().toString().hashCode()

        val user = User(
            id = userId,
            username = binding.mEditTextUsername.text.toString(),
            name = binding.mEditTextName.text.toString().trim(),
            password = binding.mEditTextPassword.text.toString(),
            bio = "",
            link = "",
            profilePicture = "",
            isVerified = false
        )

        registerViewModel.insertUser(user)
        saveLoginInfo(userId)
        startActivity(Intent(context, SecondActivity::class.java))
        requireActivity().finish()
        Toast.makeText(context, resources.getString(R.string.registered_user_successfully), Toast.LENGTH_SHORT).show()

    }

    private fun saveLoginInfo(userId: Int) {

        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.user_info), Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt("id", userId)
        editor.putString("username", binding.mEditTextUsername.text.toString())
        editor.putString("password", binding.mEditTextPassword.text.toString())
        editor.apply()

    }

}