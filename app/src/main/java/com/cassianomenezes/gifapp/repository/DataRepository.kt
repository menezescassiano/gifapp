package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.home.model.GifData
import okhttp3.ResponseBody
import retrofit2.Response

class DataRepository(private val service: ServiceAPI) : ServiceAPI {

    override suspend fun getData(): Response<GifData> = service.getData()

}