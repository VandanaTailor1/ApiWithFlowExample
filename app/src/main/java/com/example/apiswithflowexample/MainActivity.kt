package com.example.apiswithflowexample

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.apiswithflowexample.base.ApiResponseCallBack
import com.example.apiswithflowexample.databinding.ActivityMainBinding
import com.example.apiswithflowexample.viewmodel.ApodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apodViewModel: ApodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelInit()
        attachObserver()
        apiCall()
    }

    private fun viewModelInit() {
        apodViewModel = ViewModelProvider(this).get(ApodViewModel::class.java)
    }

    private fun attachObserver() {
        lifecycleScope.launch {
            apodViewModel._statusOnboardingResponseFlow.collect() {
                when (it) {
                    is ApiResponseCallBack.Failed -> {
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    is ApiResponseCallBack.Success -> {
                        Toast.makeText(this@MainActivity, it.data.message, Toast.LENGTH_SHORT)
                            .show()
                        Glide.with(this@MainActivity).load(it.data.message).into(binding.image)
                    }

                    is ApiResponseCallBack.Loading -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Please wait a second",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {

                    }
                }

            }
        }
    }

    private fun apiCall() {
        binding.apiCallButton.setOnClickListener {
            apodViewModel.getApodData()
        }
    }


}