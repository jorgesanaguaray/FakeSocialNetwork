package com.jorgesanaguaray.fakesocialnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jorgesanaguaray.fakesocialnetwork.databinding.ActivityThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}