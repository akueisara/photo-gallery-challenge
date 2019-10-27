package com.example.photogallerychallenge.data

import androidx.paging.DataSource
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.data.network.UnsplashApiService
import androidx.lifecycle.MutableLiveData

class PhotoDataSourceFactory(private val apiService: UnsplashApiService): DataSource.Factory<Int, Photo>() {

    override fun create(): DataSource<Int, Photo> {
        val dataSource = PhotoDataSource(apiService)
        return dataSource
    }

}