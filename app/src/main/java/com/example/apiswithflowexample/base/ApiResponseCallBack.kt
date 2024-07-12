package com.example.apiswithflowexample.base

sealed class ApiResponseCallBack<T> {

    class Failed<T>(val message: String) : ApiResponseCallBack<T>()
    class Loading<T> : ApiResponseCallBack<T>()
    class Success<T>(val data: T) : ApiResponseCallBack<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}