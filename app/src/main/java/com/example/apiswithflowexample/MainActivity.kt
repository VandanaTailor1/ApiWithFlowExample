package com.example.apiswithflowexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.apiswithflowexample.databinding.ActivityMainBinding
import com.example.apiswithflowexample.viewmodel.ApodViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apodViewModel: ApodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun viewModelInit() {
        apodViewModel = ViewModelProvider(this).get(ApodViewModel::class.java)
    }

    private fun attachObserver() {
        lifecycleScope.launch {
            apodViewModel._statusOnboardingResponseFlow.collect()
        }
    }

    private fun apiCall() {
    }


}