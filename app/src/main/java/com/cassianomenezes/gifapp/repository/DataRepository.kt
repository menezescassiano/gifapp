package com.cassianomenezes.gifapp.repository

import okhttp3.ResponseBody
import retrofit2.Response

class DataRepository(private val service: ServiceAPI) : ServiceAPI {

    override suspend fun getData(): Response<ResponseBody> = service.getData()

}