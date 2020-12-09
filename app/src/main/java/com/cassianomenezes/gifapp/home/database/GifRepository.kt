package com.cassianomenezes.gifapp.home.database

import com.cassianomenezes.gifapp.home.model.DataResult
import com.cassianomenezes.gifapp.home.model.Gif

interface GifRepository {

    suspend fun insertAll(gif: GifObject)

    suspend fun getAll(): List<GifObject>

    suspend fun delete(gif: GifObject)/*: List<Todo>*/

    suspend fun findByUrl(id: String): GifObject

    suspend fun getById(id: String): GifObject

    suspend fun getMyData(gifObjects: ArrayList<Gif>): DataResult<ArrayList<GifObject>>

    suspend fun gifCrud(gifObject: GifObject)
}