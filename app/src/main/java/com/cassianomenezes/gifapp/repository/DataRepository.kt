package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.BuildConfig
import com.cassianomenezes.gifapp.home.database.GifObject
import com.cassianomenezes.gifapp.home.model.DataResult
import com.cassianomenezes.gifapp.home.model.GifData
import retrofit2.Response

class DataRepository(private val service: ServiceAPI) : SafeRequest(), DataContract {

    companion object {
        const val LIMIT = "20"
        const val OFFSET = "0"
        const val RATING = "g"
        const val LANG = "en"
    }

    suspend fun getData(string: String): DataResult<GifData> { return getData(getParams(string)) }

    suspend fun getTrending(): DataResult<GifData> { return getTrending(getTrendingParams()) }
    suspend fun getGifsByIds(list: List<GifObject>): DataResult<GifData> { return getGifsByIds(getGifsIdsParams(list)) }

    override suspend fun getData(options: MutableMap<String, String>): DataResult<GifData> = apiRequest { service.getData(options) }
    override suspend fun getTrending(options: MutableMap<String, String>): DataResult<GifData> = apiRequest { service.getTrending(options) }
    override suspend fun getGifsByIds(options: MutableMap<String, String>): DataResult<GifData> = apiRequest { service.getGifsByIds(options) }


        private fun getGifsIdsParams(list: List<GifObject>): MutableMap<String, String> {
            val data: MutableMap<String, String> = HashMap()
            val idsList = list.map { it.id }
            data["ids"] = idsList.toString().removePrefix("[").removeSuffix("]").replace(" ", "")
            data["api_key"] = BuildConfig.API_KEY

            return data
        }

        private fun getParams(input: String): MutableMap<String, String> {
            val data: MutableMap<String, String> = HashMap()
            data["api_key"] = BuildConfig.API_KEY
            data["q"] = input
            data["limit"] = LIMIT
            data["offset"] = OFFSET
            data["rating"] = RATING
            data["lang"] = LANG

            return data
        }

        private fun getTrendingParams(): MutableMap<String, String> {
            val data: MutableMap<String, String> = HashMap()
            data["api_key"] = BuildConfig.API_KEY
            data["limit"] = LIMIT
            data["rating"] = LANG

            return data
        }

}