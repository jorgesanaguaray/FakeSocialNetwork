package com.jorgesanaguaray.fakesocialnetwork.register.presentation

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
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.SecondActivity
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel = ViewModelProvider(this).get()

        signUpClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signUpClick() {

        binding.mSignUp.setOnClickListener {

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

    }

    private fun isUsernameAvailable() {

        val result = registerViewModel.isUsernameAvailable(binding.mEditTextUsername.text.toString())

        if (result) {
            insertUser()
        } else {
            binding.mEditTextUsername.error = resources.getString(R.string.username_not_available_try_another)
        }

    }

    private fun insertUser() {

        val user = User(
            id = null,
            username = binding.mEditTextUsername.text.toString(),
            name = binding.mEditTextName.text.toString().trim(),
            password = binding.mEditTextPassword.text.toString()
        )

        registerViewModel.insertUser(user)
        startActivity(Intent(context, SecondActivity::class.java))
        Toast.makeText(context, "The user has been successfully registered.", Toast.LENGTH_SHORT).show()

    }

}