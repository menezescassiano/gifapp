package com.cassianomenezes.gifapp.home.database

interface GifRepository {

    suspend fun insertAll(gif: GifObject)

    suspend fun getAll(): List<GifObject>

    suspend fun delete(gif: GifObject)/*: List<Todo>*/

    suspend fun findByUrl(id: String): GifObject

    suspend fun getById(id: String): GifObject

}