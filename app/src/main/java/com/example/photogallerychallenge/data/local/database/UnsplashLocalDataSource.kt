package com.example.photogallerychallenge.data.local.database

import androidx.paging.DataSource
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer

interface UnsplashLocalDataSource {
    fun insert(networkPhotosContainer: NetworkPhotosContainer, insertFinished: () -> Unit)

    fun getPhotos(): DataSource.Factory<Int, DatabasePhoto>

    fun getPhoto(photoId: String): DatabasePhoto?

    fun updatePhoto(photo: DatabasePhoto)

    fun updatePhotoLikeStatus(photoId: String, liked: Boolean)

    fun clearPhotos()
}