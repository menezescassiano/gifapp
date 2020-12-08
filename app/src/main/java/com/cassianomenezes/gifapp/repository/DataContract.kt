package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.home.model.DataResult
import com.cassianomenezes.gifapp.home.model.GifData

interface DataContract {

    suspend fun getData(options: MutableMap<String, String>): DataResult<GifData>
    suspend fun getTrending(options: MutableMap<String, String>): DataResult<GifData>
    suspend fun getGifsByIds(options: MutableMap<String, String>): DataResult<GifData>

}