package com.example.apiswithflowexample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiswithflowexample.base.ApiResponseCallBack
import com.example.apiswithflowexample.model.ApodResponse
import com.example.apiswithflowexample.repositry.ApodRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val apodRepositry: ApodRepositry) : ViewModel() {
    private val statusOnboardingResponseFlow =
        MutableStateFlow<ApiResponseCallBack<ApodResponse>?>(null)

    val _statusOnboardingResponseFlow: StateFlow<ApiResponseCallBack<ApodResponse>?> =
        statusOnboardingResponseFlow

    fun getApodData() {

        viewModelScope.launch(Dispatchers.IO) {

            apodRepositry.getApod().catch {

                Log.d("222", "~~callOnboarding~excep~~~" + it.message)
            }
                .collect {
                    statusOnboardingResponseFlow.value = it
                }


        }
    }


}