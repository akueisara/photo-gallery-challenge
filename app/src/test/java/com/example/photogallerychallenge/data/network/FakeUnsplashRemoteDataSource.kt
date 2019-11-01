package com.example.photogallerychallenge.data.network

import com.example.photogallerychallenge.data.model.*

class FakeUnsplashRemoteDataSource(val photos: MutableList<Photo>? = mutableListOf()): UnsplashRemoteDataSource {

    override fun loadPhotos(
        page: Int,
        itemsPerPage: Int,
        onSuccess: (networkPhotosContainer: NetworkPhotosContainer) -> Unit,
        onError: (UnsplashAPIError) -> Unit) {
        photos?.let {
            onSuccess(NetworkPhotosContainer(it))
        }
        onError(UnsplashAPIError(null, null, null))
    }

    override suspend fun getPhoto(photoId: String,
                         onSuccess: (networkPhotoContainer: NetworkPhotoContainer) -> Unit,
                         onError: (error: UnsplashAPIError) -> Unit) {

        photos?.firstOrNull { it.id == photoId }?.let {
            onSuccess(NetworkPhotoContainer(it)) }
        onError(UnsplashAPIError(null, null, null))
    }
}