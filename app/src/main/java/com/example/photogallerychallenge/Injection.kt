package com.example.photogallerychallenge

import android.content.Context
import com.example.photogallerychallenge.data.database.UnsplashDatabase
import com.example.photogallerychallenge.data.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.example.photogallerychallenge.repository.UnsplashRepository
import java.util.concurrent.Executors

object Injection {

    private fun provideCache(context: Context): UnsplashLocalCache {
        val database = UnsplashDatabase.getInstance(context)
        return UnsplashLocalCache(database.unsplashDao(), Executors.newSingleThreadExecutor())
    }

    fun provideUnsplashRepository(context: Context): UnsplashRepository {
        return UnsplashRepository(UnsplashApi.unsplashApiService, provideCache(context))
    }

}