package com.example.photogallerychallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.data.network.UnsplashApiService
import com.example.photogallerychallenge.data.network.loadPhotos
import timber.log.Timber

class PhotoBoundaryCallback(private val service: UnsplashApiService, private val cache: UnsplashLocalCache) : PagedList.BoundaryCallback<DatabasePhoto>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<UnsplashAPIError>()
    val networkErrors: LiveData<UnsplashAPIError>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        Timber.d("onZeroItemsLoaded")
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: DatabasePhoto) {
        Timber.d("onItemAtEndLoaded")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        loadPhotos(service, lastRequestedPage,
            NETWORK_PAGE_SIZE, { networkPhotoContainer ->
            cache.insert(networkPhotoContainer) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }
}