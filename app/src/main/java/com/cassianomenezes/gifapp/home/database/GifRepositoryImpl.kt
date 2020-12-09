package com.cassianomenezes.gifapp.home.database

import com.cassianomenezes.gifapp.home.model.DataResult
import com.cassianomenezes.gifapp.home.model.Gif
import com.cassianomenezes.gifapp.internal.RequestStatus

class GifRepositoryImpl(val gifDao: GifDao): GifRepository {

    override suspend fun insertAll(gif: GifObject) {
        gifDao.insertAll(GifObject(id = gif.id, title = gif.title, url = gif.url, added = gif.added))
    }

    override suspend fun getAll(): List<GifObject> {
        return gifDao.getAll()
    }

    override suspend fun delete(gif: GifObject) {
        gifDao.delete(gif)
    }

    override suspend fun getById(id: String): GifObject {
        return gifDao.getById(id)
    }

    override suspend fun findByUrl(id: String): GifObject {
        return gifDao.findByUrl(id)
    }

    override suspend fun getMyData(gifObjects: ArrayList<Gif>): DataResult<ArrayList<GifObject>> {
        val newGifObjects = ArrayList<GifObject>()
        return try {
            gifObjects.forEach {
                when {
                    getById(it.id) != null ->
                        newGifObjects.add(GifObject(it.id, it.title, it.images.originalDetail.url, true))

                    else ->
                        newGifObjects.add(GifObject(it.id, it.title, it.images.originalDetail.url, false))

                }
            }
            DataResult(RequestStatus.SUCCESS, newGifObjects)
        } catch (e: Exception){
            print("Error: $e")
            DataResult(status = RequestStatus.ERROR)
        }
    }

    override suspend fun gifCrud(gifObject: GifObject) {
        try {
            gifObject.run {
                added = !added
                when {
                    added -> insertAll(this)
                    else -> delete(this)
                }
            }

        } catch (e: Exception){
            println("Error: $e")
        }
    }

}