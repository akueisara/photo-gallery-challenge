package com.example.photogallerychallenge.repository

import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto

interface Repository {
    fun loadPhotos(): Result<PagedList<DatabasePhoto>>
    fun reloadPhotos()

    suspend fun loadPhoto(photoId: String): Result<DatabasePhoto>
}