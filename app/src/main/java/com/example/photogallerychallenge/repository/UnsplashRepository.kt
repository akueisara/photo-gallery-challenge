package com.example.photogallerychallenge.repository

import androidx.paging.LivePagedListBuilder
import com.example.photogallerychallenge.data.PhotoBoundaryCallback
import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.data.database.DatabaseUser
import com.example.photogallerychallenge.data.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.model.LoadPhotoResult
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.network.UnsplashApiService
import timber.log.Timber

class UnsplashRepository(private val service: UnsplashApiService, private val cache: UnsplashLocalCache) {

    suspend fun loadPhotos(): LoadPhotoResult {
        Timber.d("loadDatebasePhotos")

        val dataSourceFactory = cache.getPhotos()

        val boundaryCallback = PhotoBoundaryCallback(service, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return LoadPhotoResult(data, networkErrors)
    }

    suspend fun getUsers(photos: List<DatabasePhoto>): List<DatabaseUser> {
        return cache.getUsers(photos)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 50
    }
}
