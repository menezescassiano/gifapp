package com.cassianomenezes.gifapp.home.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GifObject::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao

    companion object {
        @Volatile
        var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "gif-database").build()
    }
}