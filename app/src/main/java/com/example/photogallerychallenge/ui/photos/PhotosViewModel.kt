package com.example.photogallerychallenge.ui.photos

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.util.Event
import com.example.photogallerychallenge.repository.LoadPhotosResult
import com.example.photogallerychallenge.repository.UnsplashRepository

class PhotosViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val _loadPhotosResult = MutableLiveData<LoadPhotosResult>()
    private val loadPhotosResult: LiveData<LoadPhotosResult> = _loadPhotosResult

    val photos: LiveData<PagedList<DatabasePhoto>> = Transformations.switchMap(loadPhotosResult) { it.data }
    val dataLoading: LiveData<Boolean> = Transformations.switchMap(loadPhotosResult) { it.dataLoading }
    val networkError: LiveData<UnsplashAPIError> = Transformations.switchMap(loadPhotosResult) { it.error }

    private val _openPhotoEvent = MutableLiveData<Event<String>>()
    val openPhotoEvent: LiveData<Event<String>> = _openPhotoEvent

    init {
        _loadPhotosResult.value = repository.loadPhotos()
    }

    fun reloadPhotos() {
        repository.reloadPhotos()
    }

    fun openPhoto(photoId: String) {
        _openPhotoEvent.value = Event(photoId)
    }
}