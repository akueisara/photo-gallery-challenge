package com.example.photogallerychallenge.ui.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.repository.Result
import com.example.photogallerychallenge.repository.UnsplashRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PhotoDetailViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _loadPhotoResult = MutableLiveData<Result<DatabasePhoto>>()
    val loadPhotoResult: LiveData<Result<DatabasePhoto>> = _loadPhotoResult

    val photo: LiveData<DatabasePhoto> = Transformations.switchMap(loadPhotoResult) { it.data }
//    val dataLoading: LiveData<Boolean> = Transformations.switchMap(loadPhotoResult) { it.dataLoading }
    val error: LiveData<UnsplashAPIError?> = Transformations.switchMap(loadPhotoResult) { it.error }

    private val _photoViewerImageViewUrl = MutableLiveData<String>()
    val photoViewerImageViewUrl: LiveData<String> = _photoViewerImageViewUrl

    fun getPhoto(photoId: String?) {
        if(photo.value?.views != null) {
            return
        }
        photoId?.let {
            viewModelScope.launch {
                _loadPhotoResult.value = repository.loadPhoto(photoId)
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
