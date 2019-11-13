package com.example.photogallerychallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.local.database.UnsplashLocalDataSource
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.data.network.UnsplashRemoteDataSource
import timber.log.Timber

class PhotoBoundaryCallback(private val query: String, private val remoteDataSource: UnsplashRemoteDataSource, private val localDataSource: UnsplashLocalDataSource) : PagedList.BoundaryCallback<DatabasePhoto>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

    private var lastRequestedPage = 1

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _networkError = MutableLiveData<UnsplashAPIError>()
    val networkError: LiveData<UnsplashAPIError?> = _networkError

    override fun onZeroItemsLoaded() {
        Timber.d("onZeroItemsLoaded")
        requestAndSaveData(query = query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: DatabasePhoto) {
        Timber.d("onItemAtEndLoaded")
        requestAndSaveData(query = query)
    }

    fun requestAndSaveData(query: String) {
        if(_dataLoading.value == true) {
            return
        }
        _dataLoading.value = true
        remoteDataSource.loadPhotos(lastRequestedPage, NETWORK_PAGE_SIZE, query, { networkPhotoContainer ->
            localDataSource.insert(networkPhotoContainer) {
                    lastRequestedPage++
                    _dataLoading.postValue(false)
                }
            }, {
                _networkError.postValue(it)
                _dataLoading.postValue(false)
            })
    }
}