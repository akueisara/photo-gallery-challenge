package com.example.photogallerychallenge.data.local.database

import androidx.paging.DataSource
import com.example.photogallerychallenge.data.model.*
import timber.log.Timber
import java.util.concurrent.Executor

class UnsplashLocalCache(private val unsplashDao: UnsplashDao, private val ioExecutor: Executor): UnsplashLocalDataSource {

    override fun insert(networkPhotosContainer: NetworkPhotosContainer, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Timber.d("inserting ${networkPhotosContainer.asDatabaseModel().size} photos")
            unsplashDao.insertPhotos(*networkPhotosContainer.asDatabaseModel())
            for(photo in networkPhotosContainer.asDomainModel()) {
                unsplashDao.insertUser(photo.user.asDatabaseModel())
            }
            insertFinished()
        }
    }

    override fun getPhotos(): DataSource.Factory<Int, DatabasePhoto> {
        return unsplashDao.getPhotos()
    }

    override fun getPhoto(photoId: String): DatabasePhoto? {
        Timber.d("get photo $photoId from DB")
        return unsplashDao.getPhoto(photoId)
    }

    override fun updatePhoto(photo: DatabasePhoto) {
        ioExecutor.execute {
            val updateRow = unsplashDao.updatePhoto(photo)
            Timber.d("Update photo $photo row $updateRow")
        }
    }
}
