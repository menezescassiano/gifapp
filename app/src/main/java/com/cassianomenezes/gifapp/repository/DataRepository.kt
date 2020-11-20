package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.home.model.GifData
import retrofit2.Response

class DataRepository(private val service: ServiceAPI) : ServiceAPI {

    override suspend fun getData(options: MutableMap<String, String>): Response<GifData> = service.getData(options)
    override suspend fun getTrending(options: MutableMap<String, String>): Response<GifData>  = service.getTrending(options)

}