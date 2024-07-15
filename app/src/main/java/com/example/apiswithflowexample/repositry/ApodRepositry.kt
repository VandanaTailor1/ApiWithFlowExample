package com.example.apiswithflowexample.repositry

import android.util.Log
import com.example.apiswithflowexample.base.ApiResponseCallBack
import com.example.apiswithflowexample.model.ApodResponse
import com.example.apiswithflowexample.network.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class ApodRepositry @Inject constructor(private val apiInterface: ApiInterface) {

    fun getApod(): Flow<ApiResponseCallBack<ApodResponse>> = flow {
        emit(ApiResponseCallBack.loading())
        emit(ApiResponseCallBack.Success(apiInterface.getApodData()))
    }.catch { e ->
        Log.d("222", "~~~it.message~fail~" + e)
        Log.d("222", "~~~it.message~fail~$e")
        val errorMessage = when (e) {
            is HttpException -> {
                when (e.code()) {
                    401 -> "Unauthorized: ${e.message}"
                    403 -> "Forbidden: ${e.message}"
                    404 -> "Not Found: ${e.message}"
                    else -> "HTTP error: ${e.code()}"
                }
            }
            is java.net.UnknownHostException -> "Unknown host: ${e.message}"
            else -> "Unknown error: ${e.message}"
        }
        Log.d("222", "~errorMessage~~~$errorMessage")
        emit(ApiResponseCallBack.failed(errorMessage.toString()/*,errorcode*/))
    }.flowOn(Dispatchers.IO)

}