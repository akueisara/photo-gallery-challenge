package com.example.photogallerychallenge.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.asDatabaseModel
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.data.local.database.DatabaseUser
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UnsplashRepository(private val service: UnsplashApiService, private val cache: UnsplashLocalCache) {

    fun loadPhotos(): LoadPhotosResult {
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

        return LoadPhotosResult(data, networkErrors)
    }

    suspend fun loadPhoto(photoId: String, liveDataPhoto: MutableLiveData<DatabasePhoto>, error: MutableLiveData<UnsplashAPIError>) {
        withContext(Dispatchers.IO) {
            liveDataPhoto.postValue(cache.getPhoto(photoId))
            getPhoto(service, photoId, { networkPhotoContainer ->
                val databasePhoto = networkPhotoContainer.asDatabaseModel()
                databasePhoto.let {
                    cache.updatePhoto(it) {
                        liveDataPhoto.postValue(it)
                    }
                }
            }, {
                error.postValue(it)
            })
        }
    }

    suspend fun loadUser(userId: String, liveDataUser: MutableLiveData<DatabaseUser>) {
        withContext(Dispatchers.IO) {
            Timber.d("loadUser")
            liveDataUser.postValue(cache.getUser(userId))
        }

    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 30
    }
}


