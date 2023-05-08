package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorgesanaguaray.fakesocialnetwork.MainActivity
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logOutClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logOutClick() {

        binding.mLogOut.setOnClickListener {

            // Delete login info
            val sharedPref = activity?.getSharedPreferences(getString(R.string.login_info), Context.MODE_PRIVATE)
            val editor = sharedPref!!.edit()
            editor.remove("username")
            editor.remove("password")
            editor.apply()

            // Go MainActivity
            startActivity(Intent(context, MainActivity::class.java))

        }

    }

}