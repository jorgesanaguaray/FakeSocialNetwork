package com.jorgesanaguaray.fakesocialnetwork.authentication.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.SecondActivity
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(this).get()

    }

    override fun onResume() {
        super.onResume()

        binding.mLogin.setOnClickListener {
            validateLoginCredentials()
        }

        binding.mSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_mLoginNavigation_to_mRegisterNavigation)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun validateLoginCredentials() {

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

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    private fun isLoginSuccessful() {

        viewModel.isLoginSuccessful(
            username = binding.mEditTextUsername.text.toString(),
            password = binding.mEditTextPassword.text.toString()
        )

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.CREATED) {

                viewModel.loginState.collect {

                    if (it.isLoginSuccessful) {

                        saveLoginInfo()
                        startActivity(Intent(context, SecondActivity::class.java))
                        requireActivity().finish()
                        Toast.makeText(context, resources.getString(R.string.login_successful), Toast.LENGTH_SHORT).show()

                    } else {

                        showLoginFail()

                    }

                }

            }

        }

    }

    private fun saveLoginInfo() {

        viewModel.user.observe(viewLifecycleOwner) {
            viewModel.saveLoginInfo(it.id, it.username, it.password)
        }

    }

    private fun showLoginFail() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.login_failed)
        builder.setMessage(R.string.the_username_and_password_you_entered_do_not_match_any_account)
        builder.setPositiveButton(R.string.try_again) { _, _ -> }
        builder.setCancelable(false)
        builder.create().show()

    }

}