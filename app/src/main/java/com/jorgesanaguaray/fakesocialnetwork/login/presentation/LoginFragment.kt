package com.jorgesanaguaray.fakesocialnetwork.login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.SecondActivity
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        loginViewModel = ViewModelProvider(this).get()

        logInClick()
        signUpClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun logInClick() {
        
        binding.mLogIn.setOnClickListener {

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

                    isLoginSuccessful()

                }

            }
            
        }
        
    }

    private fun signUpClick() {

        binding.mSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_mLoginNavigation_to_mRegisterNavigation)
        }

    }
    
    private fun isLoginSuccessful() {

        val result = loginViewModel.isLoginSuccessful(
            username = binding.mEditTextUsername.text.toString(),
            password = binding.mEditTextPassword.text.toString()
        )

        if (result) {

            saveLoginInfo()
            startActivity(Intent(context, SecondActivity::class.java))
            Toast.makeText(context, "Log in successful.", Toast.LENGTH_SHORT).show()

        } else {
            showLoginFailedDialog()
        }
        
    }

    private fun saveLoginInfo() {

        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.login_info), Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putString("username", binding.mEditTextUsername.text.toString())
        editor.putString("password", binding.mEditTextPassword.text.toString())
        editor.apply()

    }

    private fun showLoginFailedDialog() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.login_failed)
        builder.setMessage(R.string.the_username_and_password_you_entered_do_not_match_any_account)
        builder.setPositiveButton(R.string.try_again) { _, _ -> }
        builder.setCancelable(false)
        builder.create().show()

    }

}