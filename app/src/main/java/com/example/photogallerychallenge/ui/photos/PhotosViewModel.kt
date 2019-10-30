package com.example.photogallerychallenge.ui.photos

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.util.Event
import com.example.photogallerychallenge.data.network.LoadPhotosResult
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.repository.UnsplashRepository

class PhotosViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val _loadPhotosResult = MutableLiveData<LoadPhotosResult>()
    private val loadPhotosResult: LiveData<LoadPhotosResult> = _loadPhotosResult

    val photos: LiveData<PagedList<DatabasePhoto>> = Transformations.switchMap(loadPhotosResult) { it.data }
    val networkErrors: LiveData<UnsplashAPIError> = Transformations.switchMap(loadPhotosResult) { it.networkErrors }

    private val _openPhotoEvent = MutableLiveData<Event<String>>()
    val openPhotoEvent: LiveData<Event<String>> = _openPhotoEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        _loadPhotosResult.value = repository.loadPhotos()
    }

    fun refreshLoadPhotos() {
        photos.value?.dataSource?.invalidate()
    }

    fun openPhoto(photoId: String) {
        _openPhotoEvent.value = Event(photoId)
    }
}