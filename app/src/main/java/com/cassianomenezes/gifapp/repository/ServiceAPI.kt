package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.home.model.GifData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ServiceAPI {

    @GET("v1/gifs/search?api_key=ymKz31TAU50ORG1hyydcSZh0lag7atly&q=cheeseburgers&limit=2&offset=0&rating=g&lang=en")
    suspend fun getData(): Response<GifData>
}