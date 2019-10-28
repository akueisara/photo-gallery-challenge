package com.example.photogallerychallenge.ui.photos

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.data.database.DatabaseUser
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.util.Event
import com.example.photogallerychallenge.data.model.LoadPhotoResult
import com.example.photogallerychallenge.data.model.UnsplashAPIError
import com.example.photogallerychallenge.repository.UnsplashRepository
import kotlinx.coroutines.launch

class PhotosViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val _loadPhotoResult = MutableLiveData<LoadPhotoResult>()
    val loadPhotoResult: LiveData<LoadPhotoResult> = _loadPhotoResult

    val databasePhotos: LiveData<PagedList<DatabasePhoto>> = Transformations.switchMap(loadPhotoResult) { it -> it.data }
    val networkErrors: LiveData<UnsplashAPIError> = Transformations.switchMap(loadPhotoResult) { it -> it.networkErrors }

    private val _photos = MutableLiveData<PagedList<Photo>>()
    val photos: LiveData<PagedList<Photo>> = _photos

    private val _users = MutableLiveData<List<DatabaseUser>>()
    val users: LiveData<List<DatabaseUser>> = _users

    private val _openPhotoEvent = MutableLiveData<Event<String>>()
    val openPhotoEvent: LiveData<Event<String>> = _openPhotoEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        loadDatebasePhotos()
    }

    fun loadDatebasePhotos() {
        _dataLoading.value = false
        viewModelScope.launch {
            _loadPhotoResult.value = repository.loadPhotos()
            _dataLoading.value = false
        }
    }

    fun openPhoto(photoId: String) {
        _openPhotoEvent.value = Event(photoId)
    }

    fun getUsers(photos: List<DatabasePhoto>) {
        viewModelScope.launch {
            _users.value = repository.getUsers(photos)
        }
    }

    fun loadPhotos() {
        PagedList<Photo>
        databasePhotos.value
    }
}