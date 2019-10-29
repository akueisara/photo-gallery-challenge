package com.example.photogallerychallenge.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.repository.PhotoBoundaryCallback
import com.example.photogallerychallenge.data.local.database.DatabaseUser
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.LoadPhotoResult
import com.example.photogallerychallenge.data.network.UnsplashApiService
import timber.log.Timber

class UnsplashRepository(private val service: UnsplashApiService, private val cache: UnsplashLocalCache) {

    fun loadPhotos(): LoadPhotoResult {
        Timber.d("loadPhotos")

        val dataSourceFactory = cache.getPhotos()

        val boundaryCallback =
            PhotoBoundaryCallback(service, cache)
        val networkErrors = boundaryCallback.networkErrors


        val config = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build();

        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return LoadPhotoResult(data, networkErrors)
    }

    fun getUser(userId: String): DatabaseUser? {
        return cache.getUser(userId)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 30
    }
}
