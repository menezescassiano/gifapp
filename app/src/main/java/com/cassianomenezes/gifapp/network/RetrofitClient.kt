package com.cassianomenezes.gifapp.network

import com.cassianomenezes.gifapp.repository.ServiceAPI
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://api.giphy.com/v1/gifs/"

        private const val TIMEOUT: Long = 15

        private fun getHttpClient(): OkHttpClient.Builder {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
        }

        fun getApiService(): ServiceAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getHttpClient().build())
            .build()
            .create(ServiceAPI::class.java)
    }
}