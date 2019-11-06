package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.data.model.NetworkPhotoContainer
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer

interface UnsplashRemoteDataSource {
    fun loadPhotos(page: Int, itemsPerPage: Int, query: String = "",
                   onSuccess: (networkPhotosContainer: NetworkPhotosContainer) -> Unit,
                   onError: (UnsplashAPIError) -> Unit)

    suspend fun getPhoto(photoId: String,
                         onSuccess: (networkPhotoContainer: NetworkPhotoContainer) -> Unit,
                         onError: (error: UnsplashAPIError) -> Unit)
}