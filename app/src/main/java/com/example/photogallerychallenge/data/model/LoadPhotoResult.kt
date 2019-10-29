package com.example.photogallerychallenge.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.data.model.errors.UnsplashAPIError

data class LoadPhotoResult(
    val data: LiveData<PagedList<DatabasePhoto>>,
    val networkErrors: LiveData<UnsplashAPIError>
)