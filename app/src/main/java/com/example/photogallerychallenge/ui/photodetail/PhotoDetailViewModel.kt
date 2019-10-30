package com.example.photogallerychallenge.ui.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.data.local.database.DatabaseUser
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import com.example.photogallerychallenge.repository.UnsplashRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PhotoDetailViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _photo = MutableLiveData<DatabasePhoto>()
    val photo: LiveData<DatabasePhoto> = _photo

    private val _error = MutableLiveData<UnsplashAPIError>()
    val error: LiveData<UnsplashAPIError> = _error

    private val _user = MutableLiveData<DatabaseUser>()
    val user: LiveData<DatabaseUser> = _user

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun start(photoId: String?) {
        photoId?.let {
            _dataLoading.value = true
            viewModelScope.launch {
                repository.loadPhoto(photoId, _photo, _error)
                _dataLoading.value = false
            }
        }
    }

    fun getUser(userId: String) {
        _dataLoading.value = true
        viewModelScope.launch {
            repository.loadUser(userId, _user)
            _dataLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
