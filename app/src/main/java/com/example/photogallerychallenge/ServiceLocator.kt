package com.example.photogallerychallenge

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.photogallerychallenge.data.local.database.UnsplashDatabase
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.example.photogallerychallenge.data.network.UnsplashApiDataSource
import com.example.photogallerychallenge.repository.Repository
import com.example.photogallerychallenge.repository.UnsplashRepository
import java.util.concurrent.Executors

object ServiceLocator {

    private val lock = Any()
    private var database: UnsplashDatabase? = null
    @Volatile
    var unsplashRepository: Repository? = null

    fun provideUnsplashRepository(context: Context): Repository {
        synchronized(this) {
            return unsplashRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): Repository {
        val newRepo = UnsplashRepository(UnsplashApiDataSource(UnsplashApi.unsplashApiService), createTaskLocalDataSource(context))
        unsplashRepository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): UnsplashLocalCache {
        val database = database ?: createDataBase(context)
        return UnsplashLocalCache(database.unsplashDao(), Executors.newSingleThreadExecutor())
    }

    private fun createDataBase(context: Context): UnsplashDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            UnsplashDatabase::class.java, "Unsplash.db").build()
            database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            unsplashRepository = null
        }
    }
}