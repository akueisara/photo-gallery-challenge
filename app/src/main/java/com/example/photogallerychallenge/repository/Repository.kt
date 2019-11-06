package com.example.photogallerychallenge.repository

import androidx.paging.PagedList
import com.example.photogallerychallenge.data.model.DatabasePhoto

interface Repository {
    fun loadPhotos(query: String, forceUpdate: Boolean): Result<PagedList<DatabasePhoto>>

    fun reloadPhotos(query: String)

    suspend fun loadPhoto(photoId: String): Result<DatabasePhoto>
}