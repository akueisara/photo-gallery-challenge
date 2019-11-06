package com.example.photogallerychallenge.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.local.database.UnsplashLocalDataSource
import com.example.photogallerychallenge.data.model.asDatabaseModel
import com.example.photogallerychallenge.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

// Should not need a device to run
class UnsplashRepository(private val remoteDataSource: UnsplashRemoteDataSource, private val localDataSource: UnsplashLocalDataSource) :
    Repository {

    private lateinit var boundaryCallback: PhotoBoundaryCallback

    override fun loadPhotos(query: String, forceUpdate: Boolean): Result<PagedList<DatabasePhoto>> {
        Timber.d("loadPhotos")

        if(forceUpdate) {
            localDataSource.clearPhotos()
        }

        val dataSourceFactory = localDataSource.getPhotos()

        boundaryCallback = PhotoBoundaryCallback(query, remoteDataSource, localDataSource)
        val dateLoading = boundaryCallback.dataLoading
        val networkError = boundaryCallback.networkError

        val config = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return Result(data, dateLoading, networkError)
    }

    override fun reloadPhotos(query: String) {
        boundaryCallback.requestAndSaveData(query)
    }

    override suspend fun loadPhoto(photoId: String): Result<DatabasePhoto> {
        val dataLoading = MutableLiveData<Boolean>()
        val liveDatadatabasePhoto = MutableLiveData<DatabasePhoto>()
        val error = MutableLiveData<UnsplashAPIError>()

        withContext(Dispatchers.IO) {

            liveDatadatabasePhoto.postValue(localDataSource.getPhoto(photoId))

            remoteDataSource.getPhoto(photoId, { networkPhotoContainer ->

                val databasePhoto = networkPhotoContainer.asDatabaseModel()
                liveDatadatabasePhoto.postValue(databasePhoto)

                localDataSource.updatePhoto(databasePhoto)
            }, {
                error.postValue(it)
            })
        }

        return Result(liveDatadatabasePhoto, dataLoading, error)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 100
    }
}


