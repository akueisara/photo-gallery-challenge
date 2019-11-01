package com.example.photogallerychallenge.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.asPagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto
import com.example.photogallerychallenge.data.model.NetworkPhotosContainer
import com.example.photogallerychallenge.data.model.asDatabaseModel
import com.example.photogallerychallenge.data.network.UnsplashAPIError
import kotlinx.coroutines.runBlocking
import java.util.LinkedHashMap

class FakeRepository: Repository {

    var photosServiceData: LinkedHashMap<Int, DatabasePhoto> = LinkedHashMap()

    private val observablePhotos = MutableLiveData<PagedList<DatabasePhoto>>()

    private val observableDataLoading = MutableLiveData<Boolean>()

    private val observableError = MutableLiveData<UnsplashAPIError>()

    private val observablePhoto = MutableLiveData<DatabasePhoto>()

    // Rest of class
    override fun loadPhotos(): Result<PagedList<DatabasePhoto>> {
        observablePhotos.value = photosServiceData.values.toList().asPagedList()
        observableDataLoading.value = false
        observableError.value = null
        return Result(observablePhotos, observableDataLoading, observableError)
    }

    override fun reloadPhotos() {
        observablePhotos.value = loadPhotos().data.value
    }

    override suspend fun loadPhoto(photoId: String): Result<DatabasePhoto> {
        photosServiceData.values.toList().firstOrNull { it.id == photoId }?.let {
            observablePhoto.value = it
            observableDataLoading.value = false
            observableError.value = null
            return Result(observablePhoto, observableDataLoading, observableError)
        }
        observableDataLoading.value = false
        observableError.value = UnsplashAPIError(Exception("Could not find task"))
        return Result(observablePhoto, observableDataLoading, observableError)
    }

    fun addDatabasePhotos(vararg photos: DatabasePhoto) {
        var count = 0
        for (photo in photos) {
            photosServiceData[count] = photo
            count += 1
        }
        runBlocking { reloadPhotos() }
    }

}