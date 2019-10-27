package com.example.revoluttask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photogallerychallenge.data.network.UnsplashApiService
import com.example.photogallerychallenge.ui.photos.PhotosViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiService: UnsplashApiService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            if (isAssignableFrom(PhotosViewModel::class.java)) {
                PhotosViewModel(apiService)
            } else {
                throw IllegalArgumentException("Unable to construct ${modelClass.name}")
            }
        } as T
}
