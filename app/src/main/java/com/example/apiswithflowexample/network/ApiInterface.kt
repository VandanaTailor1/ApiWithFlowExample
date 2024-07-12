package com.example.apiswithflowexample.network

import com.example.apiswithflowexample.model.ApodResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("random")
    suspend fun getApodData(): ApodResponse

}