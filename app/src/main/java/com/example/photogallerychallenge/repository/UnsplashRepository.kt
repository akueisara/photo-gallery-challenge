package com.example.photogallerychallenge.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.asDatabaseModel
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.DatabaseUser
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UnsplashRepository(private val service: UnsplashApiService, private val cache: UnsplashLocalCache) {

    private lateinit var boundaryCallback: PhotoBoundaryCallback

    fun loadPhotos(): LoadPhotosResult {
        Timber.d("loadPhotos")

        val dataSourceFactory = cache.getPhotos()

        boundaryCallback = PhotoBoundaryCallback(service, cache)

        val dateLoading = boundaryCallback.dataLoading
        val networkError = boundaryCallback.networkError

        val config = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return LoadPhotosResult(data, dateLoading, networkError)
    }

    fun reloadPhotos() {
        boundaryCallback.requestAndSaveData()
    }

    suspend fun loadPhoto(photoId: String, liveDataPhoto: MutableLiveData<DatabasePhoto>, dataLoading: MutableLiveData<Boolean>, error: MutableLiveData<UnsplashAPIError>) {
        withContext(Dispatchers.IO) {
            dataLoading.postValue(true)
            UnsplashApiHelper.getPhoto(service, photoId, { networkPhotoContainer ->
                dataLoading.postValue(false)
                val databasePhoto = networkPhotoContainer.asDatabaseModel()
                liveDataPhoto.postValue(databasePhoto)
                cache.updatePhoto(databasePhoto)
            }, {
                dataLoading.postValue(false)
                liveDataPhoto.postValue(cache.getPhoto(photoId))
                error.postValue(it)
            })
        }
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 30
    }
}


