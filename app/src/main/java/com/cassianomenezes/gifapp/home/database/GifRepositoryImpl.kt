package com.cassianomenezes.gifapp.home.database

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

}