package com.example.apiswithflowexample.di

import android.content.Context
import com.example.apiswithflowexample.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://dog.ceo/api/breeds/image/"


    @Provides
    @Singleton
    fun provideOkhttpModule(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithApiKey = originalRequest.newBuilder()
                    // Add any headers if needed
                    .addHeader("api_key", "55396ccebd884461839bbe1347399c4f")
                    .build()
                chain.proceed(requestWithApiKey)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl()) // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}
//    @Provides
//    @Singleton
//    fun provideOkhttpModule(@ApplicationContext appContext: Context): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                chain.proceed(
//                    chain.request()
//                        .newBuilder()
//                        .also {
//
//                        }.build()
//                )
//            }
//            //add timeouts, logging
//            .also { okHttpClient ->
//                okHttpClient.connectTimeout(connectionTime, TimeUnit.SECONDS)
//                okHttpClient.readTimeout(readTime, TimeUnit.SECONDS)
//            }
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideApiService(baseUrl: String, okHttpClient: OkHttpClient): ApiInterface =
//        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//            .create(ApiInterface::class.java)