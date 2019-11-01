package com.example.photogallerychallenge

import android.content.Context
import com.example.photogallerychallenge.data.local.database.UnsplashDatabase
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.example.photogallerychallenge.data.network.UnsplashApiDataSource
import com.example.photogallerychallenge.repository.UnsplashRepository
import java.util.concurrent.Executors

object Injection {

    // Alternative: Dagger 2 -- codelab https://github.com/googlecodelabs/android-dagger
    private fun provideCache(context: Context): UnsplashLocalCache {
        val database = UnsplashDatabase.getInstance(context)
        return UnsplashLocalCache(database.unsplashDao(), Executors.newSingleThreadExecutor())
    }

    fun provideUnsplashRepository(context: Context): UnsplashRepository {
        return UnsplashRepository(UnsplashApiDataSource(UnsplashApi.unsplashApiService), provideCache(context))
    }

}