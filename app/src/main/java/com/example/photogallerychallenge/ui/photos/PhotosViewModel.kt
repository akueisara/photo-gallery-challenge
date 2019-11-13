package com.example.photogallerychallenge.ui.photos

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.repository.Repository
import com.example.photogallerychallenge.util.Event
import com.example.photogallerychallenge.repository.UnsplashRepository
import kotlinx.coroutines.launch
import com.example.photogallerychallenge.repository.Result

class PhotosViewModel(private val repository: Repository) : ViewModel() {

    private val _loadPhotosResult = MutableLiveData<Result<PagedList<DatabasePhoto>>>()
    val loadPhotosResult: LiveData<Result<PagedList<DatabasePhoto>>> = _loadPhotosResult

    val photos: LiveData<PagedList<DatabasePhoto>> = Transformations.switchMap(loadPhotosResult) { it.data }
    val dataLoading: LiveData<Boolean> = Transformations.switchMap(loadPhotosResult) { it.dataLoading }
    val error: LiveData<UnsplashAPIError?> = Transformations.switchMap(loadPhotosResult) { it.error }

    private val _openPhotoEvent = MutableLiveData<Event<String>>()
    val openPhotoEvent: LiveData<Event<String>> = _openPhotoEvent

    private var lastQuery = ""

    fun loadPhotos(queryString: String, forceUpdate: Boolean = false)  {
        lastQuery = queryString
        _loadPhotosResult.value =  repository.loadPhotos(queryString, forceUpdate)
    }

    fun reloadPhotos() {
        repository.reloadPhotos(lastQuery)
    }

    fun openPhoto(photoId: String) {
        _openPhotoEvent.value = Event(photoId)
    }

    fun likePhoto(databasePhoto: DatabasePhoto) {
        viewModelScope.launch {
            if(databasePhoto.liked_by_user) {
                repository.unlikePhoto(databasePhoto.id)
            } else {
                repository.likePhoto(databasePhoto.id)
            }
        }
    }
}