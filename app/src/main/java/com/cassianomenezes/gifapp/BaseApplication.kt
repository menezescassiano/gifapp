package com.cassianomenezes.gifapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.cassianomenezes.gifapp.di.loadKoinModules
import com.cassianomenezes.gifapp.home.database.AppDatabase
import com.cassianomenezes.gifapp.home.database.GifRepository
import com.cassianomenezes.gifapp.home.database.GifRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    lateinit var gifRepositoryImpl: GifRepository
    lateinit var db: AppDatabase

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        initDB()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            loadKoinModules()
        }
    }

    private fun initDB() {
        db = AppDatabase.invoke(this)
        gifRepositoryImpl = GifRepositoryImpl(db.gifDao())
    }
}