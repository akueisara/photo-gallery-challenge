package com.example.photogallerychallenge.data.local.database

import androidx.paging.DataSource
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer
import com.example.photogallerychallenge.data.model.asDatabaseModel
import com.example.photogallerychallenge.data.model.asDomainModel
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.DatabaseUser
import timber.log.Timber
import java.util.concurrent.Executor

class UnsplashLocalCache(private val unsplashDao: UnsplashDao, private val ioExecutor: Executor) {

    fun insert(networkPhotosContainer: NetworkPhotosContainer, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Timber.d("inserting ${networkPhotosContainer.asDatabaseModel().size} photos")
            unsplashDao.insertPhotos(*networkPhotosContainer.asDatabaseModel())
            for(photo in networkPhotosContainer.asDomainModel()) {
                unsplashDao.insertUser(photo.user.asDatabaseModel())
            }
            insertFinished()
        }
    }

    fun getPhotos(): DataSource.Factory<Int, DatabasePhoto> {
        return unsplashDao.getPhotos()
    }

    fun getPhoto(photoId: String): DatabasePhoto? {
        Timber.d("get photo $photoId from DB")
        return unsplashDao.getPhoto(photoId)
    }

    fun updatePhoto(photo: DatabasePhoto) {
        ioExecutor.execute {
            val updateRow = unsplashDao.updatePhoto(photo)
            Timber.d("Update photo $photo row $updateRow")
        }
    }
}
