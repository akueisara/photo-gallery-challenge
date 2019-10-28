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
            unsplashDao.insertPhotos(networkPhotoContainer.asDatabaseModel())
            for(photo in networkPhotoContainer.asDomainModel()) {
                Timber.d("inserting user ${photo.user}")
                unsplashDao.insertUser(photo.user.asDomainModel())
            }
            insertFinished()
        }
    }

    suspend fun getPhotos(): DataSource.Factory<Int, DatabasePhoto> {
        return unsplashDao.getPhotos()
    }

    suspend fun getUsers(photos: List<DatabasePhoto>): List<DatabaseUser> {
        val users = mutableListOf<DatabaseUser>()
        for (photo in photos) {
            unsplashDao.getUserById(photo.user_id)?.let {
                users.add(it)
            }
        }
        return users
    }
}
