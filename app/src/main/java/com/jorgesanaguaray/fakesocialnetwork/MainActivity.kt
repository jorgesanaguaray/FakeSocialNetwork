package com.jorgesanaguaray.fakesocialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.jorgesanaguaray.fakesocialnetwork.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(this).get()

        if (viewModel.getUserId() != 0) {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}