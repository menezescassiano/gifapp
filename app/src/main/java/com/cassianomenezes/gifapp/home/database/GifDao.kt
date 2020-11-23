package com.cassianomenezes.gifapp.home.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GifDao {

    @Query("SELECT * FROM gif")
    suspend fun getAll(): List<GifObject>

    @Query("SELECT * FROM gif WHERE id IN (:id) LIMIT 1")
    suspend fun getById(id: String): GifObject

    @Query("SELECT * FROM gif WHERE url LIKE :url LIMIT 1")
    suspend fun findByUrl(url: String): GifObject

    @Insert
    suspend fun insertAll(vararg gifs: GifObject)

    @Delete
    suspend fun delete(gif: GifObject)

}