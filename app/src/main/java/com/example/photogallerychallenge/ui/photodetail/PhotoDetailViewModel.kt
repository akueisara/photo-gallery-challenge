package com.example.photogallerychallenge.ui.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.DatabaseUser
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.repository.UnsplashRepository
import com.example.photogallerychallenge.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class PhotoDetailViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _photo = MutableLiveData<DatabasePhoto>()
    val photo: LiveData<DatabasePhoto> = _photo

    private val _error = MutableLiveData<UnsplashAPIError>()
    val error: LiveData<UnsplashAPIError> = _error

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _photoId = MutableLiveData<String>()
    val photoId: LiveData<String> = _photoId

    private val _photoViewerImageViewUrl = MutableLiveData<String>()
    val photoViewerImageViewUrl: LiveData<String> = _photoViewerImageViewUrl

    fun start(photoId: String?) {
        photoId?.let {
            viewModelScope.launch {
                repository.loadPhoto(photoId, _photo, _dataLoading, _error)
            }
        }
    }

    fun getPhoto(photoId: String?) {
        photoId?.let {
            viewModelScope.launch {
                repository.loadPhoto(photoId, _photo, _dataLoading, _error)
            }
        }
    }

    fun openPhotoViewer(imageUrl: String) {
        _photoViewerImageViewUrl.value = imageUrl
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
