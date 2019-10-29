package com.example.photogallerychallenge.ui.photos

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.util.Event
import com.example.photogallerychallenge.data.network.LoadPhotoResult
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.repository.UnsplashRepository

class PhotosViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val _loadPhotoResult = MutableLiveData<LoadPhotoResult>()
    val loadPhotoResult: LiveData<LoadPhotoResult> = _loadPhotoResult

    val photos: LiveData<PagedList<DatabasePhoto>> = Transformations.switchMap(loadPhotoResult) { it -> it.data }
    val networkErrors: LiveData<UnsplashAPIError> = Transformations.switchMap(loadPhotoResult) { it -> it.networkErrors }

    private val _openPhotoEvent = MutableLiveData<Event<String>>()
    val openPhotoEvent: LiveData<Event<String>> = _openPhotoEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        _loadPhotoResult.value = repository.loadPhotos()
    }

    fun refreshLoadPhotos() {
        photos.value?.dataSource?.invalidate()
    }

    fun openPhoto(photoId: String) {
        _openPhotoEvent.value = Event(photoId)
    }
}