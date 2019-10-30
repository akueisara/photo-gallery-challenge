package com.example.photogallerychallenge.data.network

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.data.model.Photo


data class LoadPhotosResult(
    val data: LiveData<PagedList<DatabasePhoto>>,
    val networkErrors: LiveData<UnsplashAPIError>
)

data class LoadPhotoResult(
    val data: LiveData<DatabasePhoto>,
    val networkErrors: LiveData<UnsplashAPIError>
)