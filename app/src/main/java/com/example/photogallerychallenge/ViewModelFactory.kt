package com.example.photogallerychallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photogallerychallenge.repository.Repository
import com.example.photogallerychallenge.ui.photodetail.PhotoDetailViewModel
import com.example.photogallerychallenge.ui.photos.PhotosViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val unsplashRepository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            if (isAssignableFrom(PhotosViewModel::class.java)) {
                PhotosViewModel(unsplashRepository)
            } else if(isAssignableFrom(PhotoDetailViewModel::class.java)) {
                PhotoDetailViewModel(unsplashRepository)
            } else {
                throw IllegalArgumentException("Unable to construct ${modelClass.name}")
            }
        } as T
}
