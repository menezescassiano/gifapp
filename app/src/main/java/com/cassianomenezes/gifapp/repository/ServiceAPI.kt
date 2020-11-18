package com.cassianomenezes.gifapp.repository

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ServiceAPI {

    @GET("/api")
    suspend fun getData(): Response<ResponseBody>
}