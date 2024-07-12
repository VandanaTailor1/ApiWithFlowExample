package com.example.apiswithflowexample.di

import android.content.Context
import com.example.apiswithflowexample.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val readTime: Long = 60
private const val writeTime: Long = 60
private const val connectionTime: Long = 2

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://api.nasa.gov/planetary/"

    @Provides
    @Singleton
    fun provideOkhttpModule(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .also {

                        }.build()
                )
            }
            //add timeouts, logging
            .also { okHttpClient ->
                okHttpClient.connectTimeout(connectionTime, TimeUnit.SECONDS)
                okHttpClient.readTimeout(readTime, TimeUnit.SECONDS)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(baseUrl: String, okHttpClient: OkHttpClient): ApiInterface =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)

}