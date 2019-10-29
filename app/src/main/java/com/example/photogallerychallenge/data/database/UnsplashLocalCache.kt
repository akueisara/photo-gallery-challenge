package com.example.photogallerychallenge.data.database

import androidx.paging.DataSource
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.network.NetworkPhotoContainer
import com.example.photogallerychallenge.data.network.asDatabaseModel
import com.example.photogallerychallenge.data.network.asDomainModel
import timber.log.Timber
import java.util.concurrent.Executor

class UnsplashLocalCache(private val unsplashDao: UnsplashDao, private val ioExecutor: Executor) {

    fun insert(networkPhotoContainer: NetworkPhotoContainer, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Timber.d("inserting ${networkPhotoContainer.asDatabaseModel().size} photos")
            unsplashDao.insertPhotos(*networkPhotoContainer.asDatabaseModel())
            for(photo in networkPhotoContainer.asDomainModel()) {
                unsplashDao.insertUser(photo.user.asDatabaseModel())
            }
            insertFinished()
        }
    }

    fun getPhotos(): DataSource.Factory<Int, DatabasePhoto> {
        return unsplashDao.getPhotos()
    }

    fun getUser(userId: String): DatabaseUser? {
        return unsplashDao.getUserById(userId)
    }
}
