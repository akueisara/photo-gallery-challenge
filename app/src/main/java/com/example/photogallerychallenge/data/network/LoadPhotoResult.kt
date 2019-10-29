package com.example.photogallerychallenge.data.network

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.local.database.DatabasePhoto

data class LoadPhotoResult(
    val data: LiveData<PagedList<DatabasePhoto>>,
    val networkErrors: LiveData<UnsplashAPIError>
)