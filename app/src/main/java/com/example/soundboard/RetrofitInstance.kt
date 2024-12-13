package com.example.soundboard

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://freesound.org/apiv2/"
    private const val TAG_DEBUG = "RetrofitInstanceDebug"
    private const val TAG_INFO = "RetrofitInstanceInfo"
    private const val TAG_WARN = "RetrofitInstanceWarn"
    private const val TAG_ERROR = "RetrofitInstanceError"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val token = OAuthService.getCurrentAccessToken()
            Log.d(TAG_DEBUG, "Current access token: $token")
            if (token != null) {
                requestBuilder.header("Authorization", "Bearer $token")
                Log.d(TAG_DEBUG, "Added Bearer token to request")
            } else {
                Log.w(TAG_WARN, "No access token available")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    val api: FreesoundApi by lazy {
        Log.d(TAG_DEBUG, "Creating Retrofit instance")
        Log.i(TAG_INFO, "Base URL: $BASE_URL")
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            Log.d(TAG_DEBUG, "Retrofit instance created successfully")
            retrofit.create(FreesoundApi::class.java).also {
                Log.d(TAG_DEBUG, "FreesoundApi instance created")
            }
        } catch (e: Exception) {
            Log.e(TAG_ERROR, "Error creating Retrofit instance", e)
            throw e
        }
    }
}