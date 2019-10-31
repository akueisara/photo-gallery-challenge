package com.example.photogallerychallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.local.database.UnsplashLocalCache
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.data.network.UnsplashApiHelper
import com.example.photogallerychallenge.data.network.UnsplashApiService
import timber.log.Timber

class PhotoBoundaryCallback(private val service: UnsplashApiService, private val cache: UnsplashLocalCache) : PagedList.BoundaryCallback<DatabasePhoto>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

    private var lastRequestedPage = 1

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _networkError = MutableLiveData<UnsplashAPIError>()
    val networkError: LiveData<UnsplashAPIError> = _networkError

    override fun onZeroItemsLoaded() {
        Timber.d("onZeroItemsLoaded")
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: DatabasePhoto) {
        Timber.d("onItemAtEndLoaded")
        requestAndSaveData()
    }

    fun requestAndSaveData() {
        if(_dataLoading.value == true) {
            return
        }
        _dataLoading.value = true
        UnsplashApiHelper.loadPhotos(service, lastRequestedPage,
            NETWORK_PAGE_SIZE, { networkPhotoContainer ->
                cache.insert(networkPhotoContainer) {
                    lastRequestedPage++
                    _dataLoading.postValue(false)
                }
            }, {
                _networkError.postValue(it)
                _dataLoading.postValue(false)
            })
    }
}