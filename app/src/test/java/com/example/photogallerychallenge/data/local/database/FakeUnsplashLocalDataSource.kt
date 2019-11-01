package com.example.photogallerychallenge.data.local.database

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.photogallerychallenge.createMockDataSourceFactory
import com.example.photogallerychallenge.data.model.*
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class FakeUnsplashLocalDataSource(val databasePhotos: MutableList<DatabasePhoto>? = mutableListOf()): UnsplashLocalDataSource {
    override fun insert(networkPhotosContainer: NetworkPhotosContainer, insertFinished: () -> Unit) {
        databasePhotos?.addAll(networkPhotosContainer.asDatabaseModel())
    }

    override fun getPhotos(): DataSource.Factory<Int, DatabasePhoto> {
        return createMockDataSourceFactory(databasePhotos?.toList() ?: mutableListOf())
    }

    override fun getPhoto(photoId: String): DatabasePhoto? {
        databasePhotos?.toList()?.firstOrNull { it.id == photoId }?.let { return it }
        return null
    }

    override fun updatePhoto(photo: DatabasePhoto) {
        databasePhotos?.firstOrNull { it.id == photo.id }?.let {
            it.views = photo.views
            it.downloads = photo.downloads
            it.exif = photo.exif
            it.location = it.location
        }
    }
}