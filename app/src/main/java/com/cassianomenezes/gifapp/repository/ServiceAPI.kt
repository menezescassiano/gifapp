package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.home.model.GifData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ServiceAPI {

    @GET("v1/gifs/search")
    suspend fun getData(@QueryMap options: MutableMap<String, String>): Response<GifData>

    @GET("v1/gifs/trending")
    suspend fun getTrending(@QueryMap options: MutableMap<String, String>): Response<GifData>
}